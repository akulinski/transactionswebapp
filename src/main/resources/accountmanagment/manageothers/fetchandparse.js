
const endPoint = "/manageothers/getusers";



function fetchDataFromServer() {

    let store = $("#store-selector option:selected").text();
    postData(endPoint, {store: store}).then(data => $("#random").text(JSON.stringify(data))) // JSON-string from `response.json()` call
        .catch(error => alert(error));
}

function postData(url = ``, data = {}) {
    // Default options are marked with *
    return fetch(url, {
        method: "POST", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, cors, *same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json; charset=utf-8",
            // "Content-Type": "application/x-www-form-urlencoded",
        },
        redirect: "follow", // manual, *follow, error
        referrer: "no-referrer", // no-referrer, *client
        body: JSON.stringify(data), // body data type must match "Content-Type" header
    }).then(response => response.json());
}

$(document).ready( function() {
    $("#store-selector").on("change",fetchDataFromServer);
});