var renderKeywordRanksPage = function () {
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
};

var loadKeywordRanks = function () {
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
};
