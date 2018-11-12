function onload(){
    document.querySelector("#datePickerFrom").valueAsDate = new Date;
    document.querySelector("#datePickerTo").valueAsDate = new Date;
    let submitButton = document.querySelector("#submitButton");

    var selectId = document.getElementById("selectId");
    var selectUser = document.getElementById("selectUser");
    fetch('http://localhost:7070/getShops')
    .then(function(response) {
        return response.json();
    })
    .then(function(myJson) {
        myJson.forEach(element => {
            var option = document.createElement("option");
            option.text = element.nazwa;
            option.value = element.id_sklepu;
            selectId.add(option);
        });
    });

    selectId.onchange = ()=>{
        fetch('http://localhost:7070/users?id_sklepu='+selectId.value)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach((element)=>{
                var option = document.createElement("option");
                option.text = element.login;
                option.value = element.login;
                selectUser.add(option);
            });
        });
    };

    submitButton.onclick = ()=>{
        let dateFrom = document.querySelector("#datePickerFrom");
        let dateTo = document.querySelector("#datePickerTo");
        let selectId = document.querySelector("#selectId");
        let selectUser = document.querySelector("#selectUser");



        var url = "http://localhost:7070/getTransactions?"+dateFrom.name+'='+dateFrom.value+'&'+dateTo.name+'='+dateTo.value+'&'+selectId.name+'='+selectId.value+'&'+selectUser.name+'='+selectUser.value;
        fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach((element)=>{
                if(element.rozliczony===1)addToCard('disabled', element);
                else addToCard('', element);
            });
        });
    }
}
function addToCard(isReadOnly, jsonPart){
    let card = document.querySelector('#cardInfo');
    let div = document.createElement('div');
    let button = document.createElement('input');
    div.classList.add('col-auto');
    div.classList.add('d-inline-flex');

    button.type='button';
    button.classList.add('form-control');
    button.disabled=true;
    button.value = 'Zapisz';

    button.onclick = ()=>{
        let parent = button.parentElement;
        let elements = parent.querySelectorAll('input');
        let obj = {};
        elements.forEach((element)=>{
            if(element!=button){
                console.log(element.value);
                obj[element.id]=element.value;
            }
        });

        var url = "http://localhost:7070/updateTransactions"+serialize(obj);
        fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach((element)=>{
                if(element.rozliczony===1)addToCard('disabled', element);
                else addToCard('', element);
            });
        });
    }

    Object.keys(jsonPart).forEach(function(k){
        let element = document.createElement('input');
        let label = document.createElement('label');
        let formGroup = document.createElement('div');

        element.text = jsonPart[k];
        element.value = jsonPart[k];
        element.disabled = isReadOnly;
        element.id = k;
        element.onchange=()=>button.disabled=false;
        if(k==='id_transakcji'||k==='data_wprowadzenia')element.disabled="disabled";

        element.classList.add("form-control");
        
        label.for = element.id;
        label.innerHTML = k;

        formGroup.classList.add("mr-2");
        formGroup.classList.add("mb-2");
        formGroup.classList.add('form-group');
        formGroup.classList.add('form-group-lg');
        formGroup.appendChild(label);
        formGroup.appendChild(element);

        div.appendChild(formGroup);
    });
    div.appendChild(button);
    card.appendChild(div);
    card.appendChild(document.createElement("br"));
}

function serialize(obj) {
    return '?'+Object.keys(obj).reduce(function(a,k){a.push(k+'='+encodeURIComponent(obj[k]));return a},[]).join('&')
}