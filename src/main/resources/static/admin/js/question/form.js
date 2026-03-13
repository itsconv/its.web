$(function() {
const $modal = $('#QuestionFormModal');
if (!$modal.length) {
    return;
}

createModal({
    id: 'QuestionFormModal',
    title: '고객문의 등록',
    evt: function() {
        loadGroupList();
        bindQuestionModalEvents();
    }
});

function loadGroupList() {
    const $groupSelect = $('#questionGroupId');
    $groupSelect.empty().append('<option value="">선택해주세요</option>');

    $.ajax({
    url: '/api/groups',
    type: 'GET',
    dataType: 'json'
    })
    .then(function(res) {
    const groups = res && res.data ? res.data : [];
    groups.forEach(function(group) {
        $groupSelect.append(
            $('<option></option>').val(group.id).text(group.name)
        );
    });
    })
    .catch(function() {
    alert('부서 목록을 불러오지 못했습니다.');
    });
}

function bindQuestionModalEvents() {
    // 관련부서
    $('#questionEmailDomainSelect').off('change.questionModal').on('change.questionModal', function() {
        const isDirect = $(this).val() === 'direct';
        const $directInput = $('#questionEmailDomainDirect');

        if (isDirect) {
            $directInput.show().focus();
            return;
        }

        $directInput.hide().val('');
    });

    //저장
    $('#btnSaveQuestion').off('click.questionModal').on('click.questionModal', function() {
        const groupId = $('#questionGroupId').val();
        const groupName = $('#questionGroupId option:selected').text();
        const createName = $('#questionCreateName').val().trim();
        const title = $('#questionTitle').val().trim();
        const contents = $('#questionContents').val().trim();
        const emailLocal = $('#questionEmailLocal').val().trim();
        const selectedDomain = $('#questionEmailDomainSelect').val();
        const directDomain = $('#questionEmailDomainDirect').val().trim();
        const emailDomain = selectedDomain === 'direct' ? directDomain : selectedDomain;

        const payload = {
            title: title,
            contents: contents,
            email: emailLocal + '@' + emailDomain,
            createName: createName,
            groupId: groupId,
            groupName: groupName
        };

        $.ajax({
            url: '/api/question',
            type: 'POST',
            dataType: 'JSON',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(payload)
        })
        .then(function(res) {
            alert(res && res.message ? res.message : '저장되었습니다');
            $modal.removeClass('is-open').attr('aria-hidden', 'true');
            location.reload();
        })
        .catch(function(error) {
            const message = error && error.responseJSON && error.responseJSON.message
            ? error.responseJSON.message
            : '저장에 실패했습니다.';
            alert(message);
        });
    });
}
});