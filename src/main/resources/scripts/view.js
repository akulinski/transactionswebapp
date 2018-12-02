function onload(){
    let dateTo = new Date();
    let selectId = document.getElementById("selectId");
    dateTo.setDate(dateTo.getDate()+1);

    document.querySelector("#datePickerTo").valueAsDate = dateTo;
    document.querySelector("#datePickerFrom").valueAsDate = new Date;

    let submitButton = document.querySelector("#submitButton");

    getStores(selectId);

    selectId.onchange = onStoreSelect;
    submitButton.onclick = onButtonClick;
}
function addToCard(isReadOnly, jsonPart){
    let card = document.querySelector('#cardInfo');
    let button = createButtonT();
    let div;
    if (card.firstChild){
        console.log("Has child");
        div = genInputs(isReadOnly, jsonPart, button);
    }
    else{
        console.log("dont have child");
        div = genInputsWithLabels(isReadOnly, jsonPart, button);
    }
    addClassesToElement(div, "col-auto d-inline-flex");

    console.log(div);

    div.appendChild(button);
    card.appendChild(div);
    card.appendChild(document.createElement("br"));
}

function serialize(obj) {
    return '?'+Object.keys(obj).reduce(function(a,k){a.push(k+'='+encodeURIComponent(obj[k]));return a},[]).join('&')
}

function onStoreSelect(){
    let selectUser = document.querySelector("#selectUser");
    let selectId = document.getElementById("selectId");

    clearObj(selectUser);//Erasing all inside of select
    fetch('http://localhost:8080/users/getUsers?shopId='+selectId.value)
        .then(function(response) {
            return response.json();
        })
        .then(function(myJson) {
            myJson.forEach((element)=>{
                let option = document.createElement("option");
                option.text = element.login;
                option.value = element.login;
                selectUser.add(option);
            });
        });
}

function createButtonT(){
    let button = document.createElement('input');
    button.type='button';
    addClassesToElement(button, "btn btn-dark btn-sm");
    button.disabled=true;
    button.value = 'Zapisz';

    button.onclick = ()=>{
        let parent = button.parentElement;
        let elements = parent.querySelectorAll('input');
        console.log(elements);
        let obj = {};
        button.disabled=true;
        elements.forEach((element)=>{
            if(element!==button){
                console.log(element.value);
                obj[element.id]=element.value;
            }
        });

        let url = "http://localhost:8080/transactions/updateTransactions" + serialize(obj);
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
    return button;
}

function genInputs(isReadOnly, jsonPart, button){
    let div = document.createElement('div');
    let selectBox = createSelect("isApproved", "true false", "Tak Nie", div);
    let userId = createInput("userId", jsonPart.userEntity.id, div, isReadOnly);
    let tId = createInput("id", jsonPart.id, div, isReadOnly);
    let dateOfCreation = createCalendar("dateOfCreation", jsonPart.dateOfCreation, div, isReadOnly);
    let dateOfModification = createCalendar("dateOfModification", jsonPart.dateOfModification, div, isReadOnly);
    let modifierId = createInput("modifierId", jsonPart.modifierId, div, isReadOnly);

    let allInputs = div.querySelectorAll(".form-control");
    allInputs.forEach((input)=>{
        input.onchange=()=>button.disabled=false;
    });

    return div;
}

function genInputsWithLabels(isReadOnly, jsonPart, button){
    let div = document.createElement('div');
    let selectBox = createSelectWithLabel("isApproved", "true false", "Tak Nie", div, "Rozliczony");
    let userId = createInputWithLabel("userId", jsonPart.userEntity.id, div, isReadOnly, "id użytkownika");
    let tId = createInputWithLabel("id", jsonPart.id, div, isReadOnly, "id transakcji");
    let dateOfCreation = createCalendarWithLabel("dateOfCreation", jsonPart.dateOfCreation, div, isReadOnly, "Data utworzenia");
    let dateOfModification = createCalendarWithLabel("dateOfModification", jsonPart.dateOfModification, div, isReadOnly, "Data modyfikacji");
    let modifierId = createInputWithLabel("modifierId", jsonPart.modifierId, div, isReadOnly, "id modyfikujacego");

    let allInputs = div.querySelectorAll(".form-control");
    allInputs.forEach((input)=>{
        input.onchange=()=>button.disabled=false;
    });

    return div;
}

function onButtonClick(){
    let dateFrom = document.querySelector("#datePickerFrom");
    let dateTo = document.querySelector("#datePickerTo");
    let selectId = document.querySelector("#selectId");
    let selectUser = document.querySelector("#selectUser");
    let url = "http://localhost:8080/transactions/getTransactions?"+dateFrom.name+'='+dateFrom.value+'&'+dateTo.name+'='+dateTo.value+'&'+selectId.name+'='+selectId.value+'&'+selectUser.name+'='+selectUser.value;

    //erase all inside card
    clearObj(document.querySelector("#cardInfo"));

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

function getStores(selectId){

    fetch('http://localhost:8080/stores/getStores')
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
}