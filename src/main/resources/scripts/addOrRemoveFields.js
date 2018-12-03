
window.addEventListener("load", ()=>{
    document.querySelector("#Form").innerHTML="";
    addFields();
});

function saveTransactions(userName){
    let form = document.querySelector("#Form");
    let allGroups = form.querySelectorAll(".transaction");
    if(checkIfNulls(form)) {
        createSnackBar("Nie wprowadzono żadnej wartości w polu");
        return;
    }
    allGroups.forEach((element)=>{
        let arr = getAllValues(element);
        if(!arr)return false;
        let url = "http://localhost:8080/transactions/addTransaction?username="+userName+"&isApproved="+arr[0]+"&kartyPayback="+arr[1]+"&zwroty="+arr[2]+"&niefiskalne="+arr[3]+"&fiskalne="+arr[4]+"&fiskalnePlatnoscKarta="+arr[5];
        fetch(url)
            .then(function(response) {
                return response.json();
            })
            .then(function(myJson) {
                createSnackBar(myJson.Message);
            });
    });

    removeChild(form);
    addFields();
}

function checkIfNulls(form){
    let allElements = form.querySelectorAll("input, select");
    for(let element of allElements){
        if(element.value === null || typeof element.value === "undefined" || element.value === ""){
            createSnackBar("Jedno z pól nie ma podanej wartości");
            return true;
        }
    }
    return false;
}

function getAllValues(element){
    let arr = [];
    let val = element.querySelectorAll("input, select");
    val.forEach((eachInput)=>{
        if(eachInput.value === null || typeof eachInput.value === "undefined" || eachInput.value === ""){
            createSnackBar("Jedno z pól nie ma podanej wartości");
            return false;
        }
        else arr.push(eachInput.value);
    });
    return arr;
}

function addManyFields(){
    let numberOfFields = document.querySelector("#numberOfFields").value;
    if(numberOfFields<=0){
        createSnackBar("Zmień liczbę pól");
        return;
    }
    for(let i = 0; i<numberOfFields; i++)addFields();

    if(numberOfFields>=5&&numberOfFields!==1)createSnackBar("Utworzono "+numberOfFields+" pól");
    else if (numberOfFields===1)createSnackBar("Utworzono "+numberOfFields+" pole");
    else createSnackBar("Utworzono "+numberOfFields+" pola");
}

function keyPressed(e){
    if(e.keyCode===13||e.keyCode===9) {
        addFields();
    }
}

function addFields(){
    let container = document.getElementById("Form");
    let div = createDiv();
    if(container.firstChild){
        console.log(container);
        createInputs(div);
        div.appendChild(createButton(div));
    }
    else {
        console.log(container);
        createInputsWithLabels(div);
    }
    div.appendChild(document.createElement("br"));
    container.appendChild(div);
}

function createDiv(){
    return addClassesToElement(document.createElement("div"), "col-auto d-block transaction");
}

function createInputsWithLabels(div) {
    let selectBox = createSelectWithLabel("selectFirst", "true false", "Tak Nie", div, "Rozliczony");
    let input1 = createInputWithLabel("kartyPayback", "", div, false, "Karty payback");
    let input2 = createInputWithLabel("zwroty", "", div, false, "Zwroty");
    let input3 = createInputWithLabel("niefiskalne", "", div, false, "Niefiskalne");
    let input4 = createInputWithLabel("fiskalne", "", div, false, "fiskalne");
    let input5 = createInputWithLabel("fiskalnePlatnosckartka", "", div, false, "Fiskalne platnosc karta");
    input5.addEventListener('keydown', (e) => {
        keyPressed(e);
    });
}

function createInputs(div){
    let selectBox = createSelect("selectFirst", "true false", "Tak Nie", div);
    let input1 = createInputWithLabel("kartyPayback", "", div, false, "Karty payback");
    let input2 = createInputWithLabel("zwroty", "", div, false, "Zwroty");
    let input3 = createInputWithLabel("niefiskalne", "", div, false, "Niefiskalne");
    let input4 = createInputWithLabel("fiskalne", "", div, false, "fiskalne");
    let input5 = createInputWithLabel("fiskalnePlatnosckartka", "", div, false, "Fiskalne platnosc karta");
    input5.addEventListener('keydown', (e) => {
        keyPressed(e);
    });
}

function createButton(obj){
    let button = document.createElement("button");
    addClassesToElement(button, "btn btn-danger");
    button.innerHTML = "X";
    button.onclick = ()=>{
        removeFields(obj);
    };
    return button;
}

function removeFields(obj){
    let container = document.getElementById("Form");
    container.removeChild(obj);
}