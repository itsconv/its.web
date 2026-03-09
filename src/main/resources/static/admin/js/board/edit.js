const DETAIL_IDS = [];

$(function () {
    pageInit.load();
    pageInit.button();
});

const pageInit = {
    load: function() {
        initEditor();
    },
    button: function() {
        // 썸네일
        $('.thumb-check').on('click', function() {
            const checked = $(this).is(':checked');
            const file = $(this).closest('.bbs-file-row').find('input[type="file"]')[0].files[0];

            if(checked) {
                $('.thumb-check').not(this).prop('checked', false);
            }

            // 파일 미첨부시
            if(!file) {
                alert("파일 첨부 후 체크해주세요.");
                $(this).prop('checked', false);
            }
        });

        //저장
        $('#bbsSaveButton').on('click', function() {
            if (confirm("저장하시겠습니까?")) {
                Executor.edit.save((result) => {
                    const path = $("#bbsEditContent").data('board-type');

                    alert("저장되었습니다.");

                    window.location.href=`/admin/bbs/${path}`;
                });
            }
        });
    }
}

let editor;
function initEditor() {
    if (editor) return;

    const $editorEl = $('#bbsEditor').get(0);
    const $contents = $('#contents').get(0);

    editor = new toastui.Editor({
        el: $editorEl,
        height: '380px',
        initialEditType: 'wysiwyg',
        previewStyle: 'vertical',
        toolbarItems: [
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task'],
            ['table', 'image', 'link'],
            ['code', 'codeblock']
        ],
        initialValue: $contents.value || '',
        hooks: {
            addImageBlobHook: async function(file, callback){
                try {
                    const frm = new FormData();
                    frm.append('file', file, file.name || 'image.png');

                    $.ajax({
                        url: '/api/file/temp',
                        type: 'POST',
                        data: frm,
                        processData: false,
                        contentType: false
                    })
                    .then(function(res) {
                        const detailId = res.data.detailId;
                        const url = res.data.url;

                        if(detailId) DETAIL_IDS.push(detailId);

                        callback(url, detailId);
                    })
                    .catch(function(error){
                        console.log('Ajax error :', error);
                        alert(error.responseJSON.message);
                    });
                } catch (error) {
                    console.log("Failed to upload file:", error);
                    alert("이미지 업로드에 실패했습니다.");
                }
            }
        }
    });

    if (window.ResizeObserver) {
        const resizeObserver = new ResizeObserver(function () {
            const nextHeight = Math.max($editorEl.clientHeight, 380);
            editor.setHeight(`${nextHeight}px`);
        });

        resizeObserver.observe($editorEl);
    }
}



