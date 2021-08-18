
function check(event) {
    const url = "http://localhost:8080/gui/proceed";
    let payload = JSON.stringify({"payload":  document.getElementById('payload').value});
    const other_params = {
        headers : { "content-type" : "application/json; charset=UTF-8" },
        body : payload,
        method : "POST",
    };
        fetch(url, other_params)
            .then(async function (response) {
                if (response.ok) {
                    location.replace("http://localhost:8080/gui/getAll");
                } else {
                    let result = JSON.stringify(await response.json());
                    let objToParse = $.parseJSON(result);
                    alert(objToParse['message']);
                }
            });

    return false;
}