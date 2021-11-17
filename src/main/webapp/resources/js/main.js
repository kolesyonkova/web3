document.addEventListener("DOMContentLoaded", function () {
    $(".r-button3").click();
    $(".x-button7").click();
    drawCanvas()
});

let xVal;
let yVal;
let rVal;

$('.input_form_button_x').on('click', function (event) {
    $(this).addClass('button_x_clicked');
    $('.input_form_button_x').not(this).removeClass('button_x_clicked');
});

function changeX(link, val) {
    xVal = val;
    $('.input_form_hidden_x input[type=hidden]').val(xVal);
}

function getR() {
    return rVal;
}

$('.input_form_control_buttons_button_submit').on('click', function (event) {
    drawCanvas()
    $('.input_form_hidden_x input[type=hidden]').val(xVal);
    $('.input_form_hidden_r input[type=hidden]').val(rVal);
    drawShoot(xVal, $('.input-form_text_y').val(), rVal)
    wrongFieldX.textContent = ""
    wrongFieldY.textContent = ""
    wrongFieldR.textContent = ""
    if (!checkY()) {
        console.log("ya tyt")
        event.preventDefault()
    }
});

function checkY() {
    yVal = $('.input-form_text_y').val();
    console.log("y=" + yVal)
    if (yVal === "") {
        wrongFieldY.textContent = "Поле Y должно быть заполнено";
        return false;
    }
    yVal = yVal.replace(",", ".")
    if (!(yVal && !isNaN(yVal))) {
        wrongFieldY.textContent = "Y должен быть числом!";
        return false;
    }
    if (!((yVal >= -5) && (yVal <= 5))) {
        wrongFieldY.textContent = "Y должен принадлежать промежутку: (-5; 5)!";
        return false;
    }
    return true;
}


// $('.input_form_control_buttons_button_clear').on('click', function (event) {
//     clear()
// });

function clear() {
    clearCanvas()
    drawCanvas()
}

$('.input_form_button_r').on('click', function (event) {
    rVal = $(this).val();
    clear()
    $(this).addClass('button_r_clicked');
    $('.input_form_button_r').not(this).removeClass('button_r_clicked');
    $('.input_form_hidden_r input[type=hidden]').val(rVal);
});


let wrongFieldX = document.getElementById("wrong_field_X");
let wrongFieldY = document.getElementById("wrong_field_Y");
let wrongFieldR = document.getElementById("wrong_field_R");


function clickOnChart(canvas, event) {
    let rect = canvas.getBoundingClientRect()
    let width = canvas.width;
    let height = canvas.height;
    let x = (event.clientX - rect.left - width / 2) / step;
    let y = (height / 2 - event.clientY + rect.top) / step;
    x = x.toFixed(2).replace(".00", "");
    y = y.toFixed(2).replace(".00", "");
    if (isValid(x, y, rVal)) {
        xVal = x
        yVal = y
        $('.input-form_text_y').val(yVal);
        $(".submit").click();
    }
}

function isValid(x, y, r) {
    return (x >= -3 && x <= 5) && (y >= -5 && y <= 5) && (r >= 1 && r <= 5);
}
