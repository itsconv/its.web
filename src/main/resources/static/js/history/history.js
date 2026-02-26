$(function() {
    pageInit.load();
    pageInit.button();
});

const pageInit = {
    load: function() {
        AdminLayout.createModal({
            id: 'historyEditModal',
            title: '연혁관리',
            evt: function() {
                //수정완료
                $('#btnConfirmModify').off('click').on('click', function() {
                    const targetId = $('#historyEditModal').attr('data-history-id');
                    Executor.top.modify(targetId);
                });
            }
        });

        getTopHistory();
    },
    button: function() {

    }
}

async function getTopHistory() {
    const parent = 'TOP';

    try {
        const result = await $.ajax({
            url: `/api/history/${parent}`,
            type: `GET`,
            dataType: `JSON`,
        })
    
        renderHistoryTable(result);
    } catch (error) {
        console.log("Error : ", error);  
    }
}

function renderHistoryTable(res) {
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

    for (const history of res) {
        const subName = history.subName ?? '';

        $body.append(
            `<tr>
                <td align="center" class="start-period">${history.name}</td>
                <td align="center" class="end-period">${subName}</td>
                <td align="center" data-history-id="${history.id}">
                    <button class="h18 red_s history-edit-btn">수정</button>
                    <button class="h18 blue_s">상세</button>
                    <button class="h18 blue_s">삭제</button>
                </td>
            </tr>
            `
        );
    }

    tableRowEvt();
}

function tableRowEvt() {
   //수정 Modal 열기
    $('.history-edit-btn').off('click').on('click', function() {
        const $this = $(this);
        
        $('#startPeriod').val(
            $this
                .closest('td')
                .siblings('.start-period')
                .text()
        );

        $('#endPeriod').val(
            $this
            .closest('td')
            .siblings('.end-period')
            .text()
        );
        
        $('#historyEditModal')
            .addClass('is-open')
            .attr('aria-hidden', 'false')
            .attr('data-history-id', $this.parent().data('history-id'));
    });
}