function serialize(obj) {
    return '?'+Object.keys(obj).reduce(function(a,k){a.push(k+'='+encodeURIComponent(obj[k]));return a},[]).join('&')
}

function removeChild(element){
    element.innerHTML = '';
}

function addClassesToElement(element, str){
    str = str.split(" ");
    for(let eachClass of str) {
        element.classList.add(eachClass);
    }
    return element;
}

function createSelect(id, values, names, parent){
    let selectBox = document.createElement("select");
    values = values.split(" ");
    names = names.split(" ");

    for(let i = 0; i<values.length; i++){
        selectBox.innerHTML += "<option value="+values[i]+">"+names[i]+"</option>"
    }
    selectBox.id = id;

    addClassesToElement(selectBox, "form-control mr-2 mb-2");

    parent.appendChild(selectBox);
    return selectBox;
}

function clearObj (obj){
    obj.innerHTML = "";
}

function createCalendar(inputId, value, parent, isDisabled){
    let element = document.createElement("input");
    element.type = "date";
    element.text = inputId;
    element.valueAsDate = new Date(value);
    element.id = inputId;
    element.disabled = isDisabled;

    addClassesToElement(element, "form-control mr-2 mb-2");

    parent.appendChild(element);

    return element;
}

function createInput(inputId, value, parent, isDisabled){
    let element = document.createElement("input");
    element.text = inputId;
    element.value = value;
    element.id = inputId;
    element.disabled = isDisabled;

    addClassesToElement(element, "form-control mr-2 mb-2");

    parent.appendChild(element);

    return element;
}

function createLabel(elemId, value, divToAdd){
    let label = document.createElement("label");
    label.for="elemId";
    label.innerHTML=value;
    divToAdd.appendChild(label);
    return label;
}

function createSelectWithLabel(id, values, names, parent, labelName){
    let div = document.createElement("div");
    let label = createLabel(id, labelName, div);
    addClassesToElement(div, "form-group d-inline-block mb-2 mr-2");
    let selectBox = document.createElement("select");
    values = values.split(" ");
    names = names.split(" ");

    for(let i = 0; i<values.length; i++){
        selectBox.innerHTML += "<option value="+values[i]+">"+names[i]+"</option>"
    }
    selectBox.id = id;

    addClassesToElement(selectBox, "form-control");
    div.appendChild(selectBox);

    parent.appendChild(div);
    return selectBox;
}

function createCalendarWithLabel(inputId, value, parent, isDisabled, labelName){
    let div = document.createElement("div");
    let label = createLabel(inputId, labelName, div);
    let element = document.createElement("input");

    addClassesToElement(div, "form-group d-inline-block mb-2 mr-2");

    element.type = "date";
    element.text = inputId;
    element.valueAsDate = new Date(value);
    element.id = inputId;
    element.name = inputId;
    element.disabled = isDisabled;

    addClassesToElement(element, "form-control");

    div.appendChild(element);

    parent.appendChild(div);

    return element;
}

function createInputWithLabel(inputId, value, parent, isDisabled, labelName){
    let div = document.createElement("div");
    let label = createLabel(inputId, labelName, div);
    addClassesToElement(div, "form-group d-inline-block mb-2 mr-2");
    let element = document.createElement("input");
    element.text = inputId;
    element.value = value;
    element.id = inputId;
    element.disabled = isDisabled;
    addClassesToElement(element, "form-control");

    div.appendChild(element);

    parent.appendChild(div);

    return element;
}