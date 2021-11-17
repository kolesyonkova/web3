document.addEventListener("DOMContentLoaded", function () {
    drawCanvas()
});

let xVal;
let yVal;
let rVal = 3;

function changeX(link, val) {
    xVal = val;
    $('.input_form_hidden_x input[type=hidden]').val(xVal);
}

function getR() {
    return rVal;
}

$('.input_form_control_buttons_button_submit').on('click', function (event) {
    console.log("y=" + yVal)
    $('.input_form_hidden_x input[type=hidden]').val(xVal);
    $('.input_form_hidden_r input[type=hidden]').val(rVal);
    // wrongFieldX.textContent = ""
    // wrongFieldY.textContent = ""
    // wrongFieldR.textContent = ""
    // if (!(checkX() & checkY())) {
    //     event.preventDefault();
    // }
});

$('.input_form_button_r').on('click', function (event) {
    rVal = $(this).val();
    $(this).addClass('button_r_clicked');
    $('.input_form_button_r').not(this).removeClass('button_r_clicked');
    $('.input_form_hidden_r input[type=hidden]').val(rVal);
});


let wrongFieldX = document.getElementById("wrong_field_X");
let wrongFieldY = document.getElementById("wrong_field_Y");
let wrongFieldR = document.getElementById("wrong_field_R");

function clear() {
    clearCanvas()
    drawCanvas()
}


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
    drawShoot(xVal, yVal, rVal)
}

function isValid(x, y, r) {
    return true;
}
