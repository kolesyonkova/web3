let xval;
let yval;
let rval;
$('.input_form_button_r').on('click', function (event) {
    rval = $(this).val();
    $(this).addClass('button_r_clicked');
    $('.input_form_button_r').not(this).removeClass('button_r_clicked');
    $('.input_form_hidden_r input[type=hidden]').val(rval);
});

function changeX(link, val) {
    xval = val;
    $('.input_form_hidden_x input[type=hidden]').val(xval);
}

let wrongFieldX = document.getElementById("wrong_field_X");
let wrongFieldY = document.getElementById("wrong_field_Y");
let wrongFieldR = document.getElementById("wrong_field_R");

function readyForm(e) {
    wrongFieldX.textContent = ""
    wrongFieldY.textContent = ""
    wrongFieldR.textContent = ""
    if (!(checkX() & checkY())) {
        e.preventDefault();
    }
}

function clear() {
    clearCanvas()
    drawCanvas()
    return new Promise(function (resolve) {
            $.get('servlet', {
                'clean': true
            }).done(function (data) {
                    $("#result_table tr:gt(0)").remove();
                }
            ).fail(function (err) {
                alert(err);
            });
        }
    );
}

function checkX() {
    let x = document.getElementById("X");
    if (x.value === "") {
        wrongFieldX.textContent = "Поле X должно быть заполнено";
        return false;
    }
    x.value = x.value.substring(0, 10).replace(',', '.');
    if (!(x.value && !isNaN(x.value))) {
        wrongFieldX.textContent = "X должен быть числом!";
        return false;
    }
    if (!((x.value >= -3) && (x.value <= 5))) {
        wrongFieldX.textContent = "X должен принадлежать промежутку: (-3; 5)!";
        return false;
    }
    return true;
}

function checkY() {
    let valY = $('input[name="y"]:checked').val();
    if (valY === undefined) {
        wrongFieldY.textContent = "Выберите одно значение Y";
        return false;
    }
    return true;
}


function clickOnChart(canvas, event) {
    let rect = canvas.getBoundingClientRect()
    let width = canvas.width;
    let height = canvas.height;
    let x = (event.clientX - rect.left - width / 2) / step;
    let y = (height / 2 - event.clientY + rect.top) / step;
    // let x = (event.clientX - rect.x- width / 2) / step;
    // let y = (height / 2 - event.clientY + rect.y) / step;
    let r = $("#R").val();
    x = x.toFixed(2).replace(".00", "");
    y = y.toFixed(2).replace(".00", "");
    if (isValid(x, y, r)) {
        submit(x, y, r)
    }
    drawShoot(x, y, r)
}

function isValid(x, y, r) {
    return (x >= -3 && x <= 5) && (y >= -3 && y <= 5) && (r >= 1 && r <= 5);
}
