function onload(){
    document.querySelector("#datePickerFrom").valueAsDate = new Date;
    let dateTo = new Date();
    dateTo.setDate(dateTo.getDate()+1);
    document.querySelector("#datePickerTo").valueAsDate = dateTo;
    let submitButton = document.querySelector("#submitButton");

    let selectId = document.getElementById("selectId");
    fetch('http://localhost:8080/getStores')
    .then(function(response) {
        console.log(response);
        return response.json();
    })
    .then(function(myJson) {
        myJson.forEach(element => {
            console.log(element);
            let option = document.createElement("option");
            option.text = element.storeName;
            option.value = element.id;
            selectId.add(option);
        });
        if(myJson!==null)onStoreSelect();
    });

    selectId.onchange = onStoreSelect;

    submitButton.onclick = ()=>{
        let dateFrom = document.querySelector("#datePickerFrom");
        let dateTo = document.querySelector("#datePickerTo");
        let selectId = document.querySelector("#selectId");
        let selectUser = document.querySelector("#selectUser");



        let url = "http://localhost:8080/getTransactions?"+dateFrom.name+'='+dateFrom.value+'&'+dateTo.name+'='+dateTo.value+'&'+selectId.name+'='+selectId.value+'&'+selectUser.name+'='+selectUser.value
        console.log(url);
        fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            console.log(myJson);
            myJson.forEach((element)=>{
                if(element.isApproved===true)addToCard('disabled', element);
                else addToCard('', element);
            });
        });
    }
}
function addToCard(isReadOnly, jsonPart){
    let card = document.querySelector('#cardInfo');
    let div = document.createElement('div');
    let button = document.createElement('input');
    card.innerHTML=""; //Erasing all inside of div
    div.classList.add('col-auto');
    div.classList.add('d-inline-flex');

    button.type='button';
    button.classList.add('btn');
    button.classList.add('btn-dark');
    button.classList.add('btn-sm');
    button.disabled=true;
    button.value = 'Zapisz';

    button.onclick = ()=>{
        let parent = button.parentElement;
        let elements = parent.querySelectorAll('input');
        let obj = {};
        button.disabled=true;
        elements.forEach((element)=>{
            if(element!==button){
                console.log(element.value);
                obj[element.id]=element.value;
            }
        });

        let url = "http://localhost:8080/updateTransactions" + serialize(obj);
        console.log(url);
        fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach((element)=>{
                if(element.isApproved===true)addToCard('disabled', element);
                else addToCard('', element);
            });
        });
    };
    console.log(jsonPart);

    Object.keys(jsonPart).forEach(function(k){
        let element = document.createElement('input');
        let label = document.createElement('label');
        let formGroup = document.createElement('div');

        if(k==='userEntity') {
            element.text = "userId";
            element.value = jsonPart.id;
            element.id = "userId";
            label.innerHTML = 'userId';
        }else {
            element.text = jsonPart[k];
            element.value = jsonPart[k];
            element.id = k;
            label.innerHTML = k;
        }
        element.disabled = isReadOnly;
        element.id = k;
        element.onchange=()=>button.disabled=false;
        if(k==='id'||k==='dateOfCreation'||k==="userId")element.disabled="disabled";

        element.classList.add("form-control");
        
        label.for = element.id;

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

function onStoreSelect(){
    selectUser.innerHTML = "";//Erasing all inside of select
    fetch('http://localhost:8080/getUsers?shopId='+selectId.value)
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
}