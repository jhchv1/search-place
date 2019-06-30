var keyword;
var page;
var selectedPlace;

$('#searchForm').on('submit', function () {
    var $keyword = $('#keyword');
    if (!$keyword.val()) {
        alert('검색할 장소를 입력하세요!');
        $keyword.focus();
    } else {
        keyword = $keyword.val();
        renderSearchResultPage();
    }
    return false;
});

$(document).on('click', '#placeList a', function () {
    selectedPlace = JSON.parse($(this).data('document').replace(/'/gi, '"'));
    $('#placeName').text(selectedPlace.placeName);
    $('#categoryName').text(selectedPlace.categoryName);
    $('#roadAddressName').text(selectedPlace.roadAddressName);
    $('#addressName').text('(지번) ' + selectedPlace.addressName);
    $('#phone').text(selectedPlace.phone);
    $('#kakaomapUrl').prop('href', 'https://map.kakao.com/link/map/' + selectedPlace.id);
});

$(document).on('shown.bs.modal', '#placeDetailsModal', function () {
    if (!$('#kakaomap').length) {
        $('#modalBody').append('<div id="kakaomap" style="width:100%;height:500px;border:1px solid #dee2e6;"></div>');
    }

    var container = document.getElementById('kakaomap');
    var position = new kakao.maps.LatLng(selectedPlace.y, selectedPlace.x);
    var options = {
        center: position,
        level: 3
    };
    var kakaomap = new kakao.maps.Map(container, options);

    var content = `
        <div class="card">
            <div class="card-body">
                <div>${selectedPlace.placeName}</div>
                <div class="small text-muted">${selectedPlace.roadAddressName}</div>
                <div class="small text-muted">(지번) ${selectedPlace.addressName}</div>
            </div>
        </div>
    `;
    var customOverlay = new kakao.maps.CustomOverlay({
        position: position,
        content: content
    });
    customOverlay.setMap(kakaomap);
});

$(document).on('click', '#viewMoreButton', function () {
    loadSearchResult(keyword, ++page);
});

var renderSearchResultPage = function () {
    var $content = $('#content').empty();
    var content = `
        <h5 id="searchResultMessage" class="mb-3"></h5>
        <ul id="placeList" class="list-group">
        </ul>
        
        <div class="modal fade" id="placeDetailsModal" tabindex="-1" role="dialog" aria-labelledby="placeName" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title font-weight-bold" id="placeName"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div id="modalBody" class="modal-body">
                        <p class="small" id="categoryName"></p>
                        <p class="small" id="roadAddressName"></p>
                        <p class="small" id="addressName"></p>
                        <p class="small" id="phone"></p>
                        <h6 class="font-weight-bold">찾아가는 길
                            <small><a id="kakaomapUrl" class="ml-1" href="#" target="_blank">카카오맵에서 보기</a></small>
                        </h6>
                    </div>
                </div>
            </div>
        </div>
    `;
    $content.html(content);
    loadSearchResult(keyword, page = 1);
};

var loadSearchResult = function (keyword, page) {
    $.ajax({
        method: 'GET',
        url: `/api/search?keyword=${keyword}&page=${page}`
    })
    .done(function (response) {
        if (response.documents.length) {
            $('#searchResultMessage').text(`"${keyword}"에 대해 검색된 장소입니다.`);
            for (var i = 0; i < response.documents.length; i++) {
                var document = response.documents[i];
                var stringified = JSON.stringify(document).replace(/>/gi, '&gt;').replace(/"/gi, "'");
                var element = `
                <li class="list-group-item mb-1">
                    <a href="#" data-toggle="modal" data-target="#placeDetailsModal" data-document="${stringified}">${document.placeName}</a>
                    <span class="small text-muted">${document.categoryGroupName}</span>
                </li>
            `;
                $('#placeList').append(element);
            }

            var $viewMoreButton = $('#viewMoreButton');
            if ($viewMoreButton.length) {
                $viewMoreButton.remove();
            }
            if (!response.meta.isEnd) {
                $('#placeList').append('<button id="viewMoreButton" class="list-group-item btn btn-kakao">더보기</button>');
            }
        }
        else {
            $('#searchResultMessage').text(`"${keyword}"에 대해 검색된 장소가 없습니다.`);
        }
    })
    .fail(function () {
        alert('장소 검색 중 오류가 발생했습니다.');
    });
};

var search = function (str) {
    $('#keyword').val(str);
    keyword = str;
    renderSearchResultPage();
};
