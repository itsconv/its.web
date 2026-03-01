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
                url: `/api/history/${id}`,
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
                url: `/api/history/${id}`,
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

    const year = {
        getList(id, callback) {
            $.ajax({
                url: `/api/history/year/${id}`,
                type: 'GET',
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
        },
        create(payload, callback) {
            $.ajax({
                url: `/api/history/year`,
                type: 'POST',
                contentType: 'application/json; charset=UTF-8',
                dataType: 'JSON',
                data: JSON.stringify(payload)
            })
            .then(function() {
                if (typeof callback === 'function') callback();
            })
            .catch(function(error) {
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });   
        },
        remove(yearId, callback) {
            $.ajax({
                url: `/api/history/year/${yearId}`,
                type: 'DELETE',
                contentType: 'application/json; charset=UTF-8',
                dataType: 'JSON'
            })
            .then(function() {
                if (typeof callback === 'function') callback();
            })
            .catch(function(error) {
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });  
        }
    }

    const item = {
        modify(payload, callback) {
            $.ajax({
                url: `/api/history/item/${payload.itemId}`,
                type: 'PUT',
                contentType: 'application/json; charset=UTF-8',
                dataType: 'JSON',
                data: JSON.stringify(payload)
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error) {
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });  
        },
        create(payload, callback) {
            $.ajax({
                url: `/api/history/item`,
                type: 'POST',
                contentType: 'application/json; charset=UTF-8',
                dataType: 'JSON',
                data: JSON.stringify(payload)
            })
            .then(function(res) {
                if (typeof callback === 'function') callback(res);
            })
            .catch(function(error) {
                console.log('Ajax error :', error);
                alert(error.responseJSON.message);
            });  
        },
        remove(itemId, callback) {
            $.ajax({
                url: `/api/history/item/${itemId}`,
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
    }

    return {period, year, item};
})();
