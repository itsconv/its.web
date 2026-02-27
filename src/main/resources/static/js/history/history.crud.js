const Executor = (function() {
    function getPeriodParam() {
        const start = $('#startPeriod').val();
        const end = $('#endPeriod').val();

        return {start, end};
    }

    const period = {
        modify(id, callback) {
            const paylod = {...getPeriodParam(), id};

            $.ajax({
                url: `/api/history/${id}/modify`,
                type: 'PUT',
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(paylod),
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
        create(callback) {
            $.ajax({
                url: '/api/history/period',
                type: 'POST',
                contentType: 'application/json; charset=UTF-8',
                dataType: 'JSON',
                data: JSON.stringify(getPeriodParam())
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error) {
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });
        },
        remove(id, callback) {
            $.ajax({
                url: `/api/history/${id}/remove`,
                type: 'DELETE',
                contentType: 'application/json; charset=UTF-8',
                dataType: 'JSON'
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error) {
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });
        }
    };

    const detail = {
        modify() {
            console.log("Detail 수정");
        }
    }

    return {period, detail};
})();
