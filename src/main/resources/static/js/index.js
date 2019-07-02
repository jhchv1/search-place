(function () {
    var accessToken = sessionStorage.getItem('accessToken');
    appendHeader(accessToken !== null);
    appendContentArea();
    renderKeywordRanksPage();
})();

/////////////////////////////////////////////////////////////////

function appendHeader(isLogin) {
    var loginButton = `
        <li class="nav-item">
            <a class="nav-link text-dark" href="#" data-toggle="modal" data-target="#loginModal">로그인</a>
        </li>
    `;
    var myInfoButton = `
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                내 정보
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="javascript:renderSearchHistoryPage();">내 검색 히스토리</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="javascript:logout();">로그아웃</a>
            </div>
        </li>
    `;
    var content = `
        <div class="bg-kakao">
            <nav class="container navbar navbar-expand navbar-dark bg-kakao">
                <a class="navbar-brand text-dark font-weight-bold" href="/">장소 검색 서비스</a>
                <form id="searchForm" class="input-group mr-auto" style="width: 500px;">
                    <input type="text" id="keyword" name="keyword" class="form-control border-warning" />
                    <div class="input-group-append">
                        <button class="btn btn-warning">검색</button>
                    </div>
                </form>
                <ul class="navbar-nav">${isLogin ? myInfoButton : loginButton}</ul>
            </nav>
        </div>
    `;
    if (!isLogin) {
        content += `
            <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <form id="loginForm" method="GET" action="/login">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="idInput" placeholder="아이디" />
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control" id="passwordInput" placeholder="비밀번호" />
                                    <small id="loginFailMessage" class="text-danger" style="display: none;">아이디 또는 비밀번호가 올바르지 않습니다.</small>
                                </div>
                                <div class="form-group mb-0">
                                    <input type="submit" class="form-control btn btn-kakao" value="로그인" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }
    $('#container').append(content);
}

function appendContentArea() {
    $('#container').append('<div id="content" class="container my-5"></div>');
}

//////////////////////////////////////////////////////

function renderKeywordRanksPage() {
    var $content = $('#content').empty();
    var content = `
        <ul id="keywordRankList" class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center row">
                <span class="font-weight-bold">인기 키워드 순위</span>
                <small id="standardDateTime" class="text-muted"></small>
            </li>
        </ul>
    `;
    $content.html(content);
    loadKeywordRanks();
}

function loadKeywordRanks() {
    $.ajax({
        method: 'GET',
        url: '/api/keyword-ranks'
    })
    .done(function (response) {
        $('#standardDateTime').text(response.standardDateTime + ' 기준');

        var $keywordRankList = $('#keywordRankList');
        if (response.keywordRanks.length) {
            for (var i = 0; i < response.keywordRanks.length; i++) {
                var keywordRank = response.keywordRanks[i];
                $keywordRankList.append(`
                    <li class="list-group-item row">
                        <span class="badge col-1">${keywordRank.rank}</span>
                        <a class="text-dark" href="javascript:search('${keywordRank.keyword}');">${keywordRank.keyword}</a>
                        <small class="text-muted float-right">총 ${keywordRank.totalSearchCount}회 검색</small>
                    </li>
                `);
            }
        }
        else {
            $keywordRankList.append('<li class="list-group-item row">집계된 키워드가 없습니다.</li>');
        }
    })
    .fail(function () {
        alert('인기 키워드 순위를 로드하지 못했습니다.');
    });
}

///////////////////////////////////////////

$(document).on('submit', '#loginForm', function () {
    var $idInput = $('#idInput');
    var $passwordInput = $('#passwordInput');
    if (!$idInput.val()) {
        $idInput.focus();
        return false;
    }
    if (!$passwordInput.val()) {
        $passwordInput.focus();
        return false;
    }
    sessionStorage.setItem('basicToken', 'Basic ' + btoa($idInput.val() + ':' + $passwordInput.val()));
    requestAccessToken()
    .done(function (response) {
        sessionStorage.setItem('accessToken', response.type + ' ' + response.accessToken);
        document.location = '/';
    })
    .fail(function () {
        $('#loginFailMessage').show();
    });
    return false;
});

$(document).on('show.bs.modal', '#loginModal', function () {
    $('#idInput').val('');
    $('#passwordInput').val('');
    $('#loginFailMessage').hide();
});

$(document).on('shown.bs.modal', '#loginModal', function () {
    $('#idInput').focus();
});

function requestAccessToken() {
    return $.ajax({
        method: 'POST',
        url: '/token',
        headers: {
            Authorization: sessionStorage.getItem('basicToken')
        }
    });
}

function logout() {
    sessionStorage.removeItem('basicToken');
    sessionStorage.removeItem('accessToken');
    document.location = '/';
}

//////////////////////////////////////////////////////////////////

var keyword;
var page;
var selectedPlace;

$(document).on('submit', '#searchForm', function () {
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

function renderSearchResultPage() {
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
}

function loadSearchResult(keyword, page) {
    var headers = {};
    var accessToken = sessionStorage.getItem('accessToken');
    if (accessToken) {
        headers = {
            Authorization: accessToken
        };
    }
    $.ajax({
        method: 'GET',
        url: `/api/search?keyword=${keyword}&page=${page}`,
        headers: headers
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
    .fail(function (response) {
        response = JSON.parse(response.responseText);
        if (response.status === 401 && response.code === 'TOKEN_EXPIRED') {
            requestAccessToken()
            .done(function (response) {
                sessionStorage.setItem('accessToken', response.type + ' ' + response.accessToken);
                loadSearchResult(keyword, page);
            })
            .fail(function () {
                alert('장소 검색 중 오류가 발생했습니다.');
            });
        }
        else {
            alert('장소 검색 중 오류가 발생했습니다.');
        }
    });
}

function search(str) {
    $('#keyword').val(str);
    keyword = str;
    renderSearchResultPage();
}

////////////////////////////////////////////////////////////////////////////

function renderSearchHistoryPage() {
    var $content = $('#content').empty();
    var content = `
        <div class="container my-5">
            <h5 id="searchResultMessage" class="font-weight-bold mb-3"></h5>
            <ul id="searchHistoryList" class="list-group">
            </ul>
        </div>
    `;
    $content.html(content);
    loadSearchHistory();
}

function loadSearchHistory() {
    var headers = {};
    var accessToken = sessionStorage.getItem('accessToken');
    if (accessToken) {
        headers = {
            Authorization: accessToken
        };
    }
    $.ajax({
        method: 'GET',
        url: '/api/histories',
        headers: headers
    })
    .done(function (response) {
        if (response.length) {
            $('#searchResultMessage').text('내가 검색해 본 키워드는..');
            for (var i = 0; i < response.length; i++) {
                var history = response[i];
                var element = `
                    <li class="list-group-item mb-1 d-flex justify-content-between">
                        <a class="font-weight-bold" href="javascript:search('${history.keyword}');">${history.keyword}</a>
                        <span class="text-muted">${history.recordedDateTime}</span>
                    </li>
                `;
                $('#searchHistoryList').append(element);
            }
        }
        else {
            $('#searchResultMessage').text('장소를 검색한 히스토리가 없습니다.');
        }
    })
    .fail(function (response) {
        response = JSON.parse(response.responseText);
        if (response.status === 401 && response.code === 'TOKEN_EXPIRED') {
            requestAccessToken()
            .done(function (response) {
                sessionStorage.setItem('accessToken', response.type + ' ' + response.accessToken);
                loadSearchHistory();
            })
            .fail(function () {
                alert('내 검색 히스토리를 불러오지 못했습니다.');
            });
        }
        else {
            alert('내 검색 히스토리를 불러오지 못했습니다.');
        }
    });
}
