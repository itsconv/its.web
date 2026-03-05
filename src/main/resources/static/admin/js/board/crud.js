const Executor = (function() {
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

    return {list};
})();