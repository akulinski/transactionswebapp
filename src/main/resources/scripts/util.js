function serialize(obj) {
    return '?'+Object.keys(obj).reduce(function(a,k){a.push(k+'='+encodeURIComponent(obj[k]));return a},[]).join('&')
}

function removeChild(element){
    element.innerHTML = '';
}

function addClassesToElement(element, str){
    str = str.split(" ");
    for(eachClass of str) {
        element.classList.add(eachClass);
    }
    return element;
}