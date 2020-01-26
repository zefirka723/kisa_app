function checkHorison() {
    if(document.getElementById("docTypeForChecks").value == 'DESCRIPTION') {
        var h = document.getElementById("horIdField").value != "";
        try {
            isOk = h | (document.getElementsByName("depthsList[0].date")[0].value == '');
            document.getElementById("horisonHint").hidden = isOk;
            return isOk;

            // if ((document.getElementsByName("depthsList[0].date")[0].value == '')) {
            //     document.getElementById("horisonHint").hidden = false;
            //     return false;
            // }
        } catch (e) {
            document.getElementById("horisonHint").hidden = true;
            return true;
        }
    }
    else return true;
}

function checkCollar() {
    var isDepthFilled = document.getElementById("wellDepthField").value != "";
    if(isDepthFilled && (document.getElementById("wellCollar").value == "")) {
        document.getElementById("collarHint").hidden = false;
        return false;
    }
    else {
        document.getElementById("collarHint").hidden = true;
        return true;
    }
}

function addHorisonRow() {
    if(document.getElementById("horIdField").value != "") {
        return WellEdit.addRow('depthRowTemplate')
    }
    else {
        document.getElementById("horisonHint").hidden = false;
    }
}

function checkValid() {
    var isOk = checkHorison() && checkCollar();
    document.getElementById("errorBlock").hidden = isOk;
    document.getElementById("saveButton").disabled = !isOk;
}