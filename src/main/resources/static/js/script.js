
function check(event) {
    const url = "http://localhost:8080/api/add";
    let payload = JSON.stringify({"payload":  document.getElementById('payload').value});
    const other_params = {
        headers : { "content-type" : "application/json; charset=UTF-8" },
        body : payload,
        method : "POST",
    };
        fetch(url, other_params)
            .then(async function (response) {
                if (response.ok) {
                    location.reload();
                } else {
                    let result = JSON.stringify(await response.json());
                    let objToParse = $.parseJSON(result);
                    alert(objToParse['message']);
                }
            });

    return false;
}