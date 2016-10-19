/**
 * Created by Romique on 19.10.2016.
 */
function addcommand(){
    var message = document.getElementById("message").value;
    var response = document.getElementById("response").value;
    var xhr = new XMLHttpRequest();
    var url = "addCommand?message="+message+"&response="+response.replace(new RegExp("\n","g"), "%0A");
    xhr.open("GET", url, false);
    xhr.send();
    document.getElementById("result").innerHTML = xhr.response;
}

function getcommands(){
    var url = "/kotik/getCommands";
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, false);
    xhr.send();
    parseResponse(xhr.response);
}

function parseResponse(response){
    console.log(response);
    var r = JSON.parse(response);
    var div = document.getElementById("result");
    try {
        div.removeChild(document.getElementById("resultTable"));
    } catch (e){
    }


    var table = document.createElement("table");
    table.setAttribute("id", "resultTable");
    div.appendChild(table);

    Object.keys(r).forEach(function(message){
       var old = table.innerHTML;
       var notOld = old + "<tr><td width=300>" + message +
           "</td><td width=600>" + r[message].replace(new RegExp("\n", "g"),"<br/>") +
           "</td></tr>"
        table.innerHTML = notOld;
    });
}