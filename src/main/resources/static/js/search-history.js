var renderSearchHistoryPage = function () {
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
};

var loadSearchHistory = function () {
    $.ajax({
        method: 'GET',
        url: '/histories'
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
    .fail(function () {
        alert('내 검색 히스토리를 불러오지 못했습니다.');
    });
};