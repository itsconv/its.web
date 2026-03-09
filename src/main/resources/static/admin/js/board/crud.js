const Executor = (function() {
    function getFormParam() {
        const frm = new FormData();

        const type = $("#bbsEditContent").data('board-type').toUpperCase();
        const title = $('#title').val();
        const contents = editor.getHTML();
        const thumbnailOrder = $("input[name='thumbnailOrder']:checked").val() || "";
        
        const jsonData = {
            type,
            title,
            contents,
            thumbnailOrder,
            detailIds: DETAIL_IDS
        }

        frm.append("request", new Blob(
            [JSON.stringify(jsonData)],
            {type: "application/json"}
        ));

        // 썸네일을 order로 특정하기 위해 순서보장
        const f1 = $("#attachFile1")[0].files[0];
        const f2 = $("#attachFile2")[0].files[0];
        const f3 = $("#attachFile3")[0].files[0];
        if (f1) frm.append("file1", f1);
        if (f2) frm.append("file2", f2);
        if (f3) frm.append("file3", f3);

        return frm;
    }

    const list = {
        remove(payload, callback) {
            $.ajax({
                url: `/api/bbs`,
                type: 'DELETE',
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(payload),
                dataType: 'JSON'
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error){
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });
        },
        copy(payload, callback) {
            $.ajax({
                url: `/api/bbs/copy`,
                type: 'POST',
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(payload),
                dataType: 'JSON'
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error){
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });
        },
        move(payload, callback) {
            $.ajax({
                url: `/api/bbs/move`,
                type: 'PUT',
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(payload),
                dataType: 'JSON'
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error){
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });
        },
        order(payload, callback) {
            $.ajax({
                url: `/api/bbs/order`,
                type: 'PUT',
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(payload),
                dataType: 'JSON'
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error){
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });
        }
    }

    const edit = {
        save(callback) {
            $.ajax({
                url: '/api/bbs',
                type: 'POST',
                data: getFormParam(),
                processData: false,
                contentType: false
            })
            .then(function(res){
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error){
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });
        }
    }

    return {list, edit};
})();