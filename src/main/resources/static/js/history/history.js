$(function() {
    pageInit.load();
    pageInit.button();
});

var pageInit = {
    load: function() {
        const parent = 'TOP';

        $.ajax({
            url: `/api/history/${parent}`,
            type: `GET`,
            dataType: `JSON`,
            success: function(res) {
                const $head = $('#dataTableHead');
                const $body = $('#dataTableBody');
                
                $head.empty();
                $body.empty();

                $head.append(
                    `<tr>
                        <td>시작구간</td>
                        <td>종료구간</td>
                        <td>기능</td>
                    </tr>
                    `
                );

                for(const json of res) {
                    const subName = 
                        json.historySubName == null ? '' : json.historySubName;

                    $body.append(
                        `<tr>
                            <td align="center">${json.historyName}</td>
                            <td align="center">${subName}</td>
                            <td align="center">
                                <button class="h18 red_s">수정</button>
                                <button class="h18 blue_s">설정</button>
                                <button class="h18 blue_s">삭제</button>
                            </td>
                        </tr>
                        `
                    );
                }
            }
        });
    },
    button: function() {

    }
}