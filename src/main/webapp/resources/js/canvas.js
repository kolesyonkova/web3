let dash = 5;
let step = 50;
let canvas = document.getElementById("canvas"),
    context = canvas.getContext("2d");
canvas.width = 510;
canvas.height = 510;
let width = canvas.width;
let height = canvas.height;
canvas.addEventListener('mousedown', event => clickOnChart(canvas, event));

function drawCanvas() {
    let valR = getR() * step;

    drawAXIS()
    drawRectangle(valR)
    drawTriangle(valR)
    drawCircle(valR)
    drawPoints()
}

function drawTriangle(valR) {
    context.fillStyle = 'blue';
    context.globalAlpha = 0.6;
    context.beginPath();
    context.moveTo((width / 2) + valR / 2, height / 2);
    context.lineTo(width / 2, (height - valR) / 2);
    context.lineTo(width / 2, height / 2);
    context.fill();
}

function drawCircle(valR) {
    context.beginPath();
    context.fillStyle = 'blue';
    context.strokeStyle = 'blue';
    context.globalAlpha = 0.6;
    context.arc(width / 2, height / 2, valR / 2, Math.PI, 3 * Math.PI / 2);
    context.lineTo(width / 2, height / 2)
    context.fill();
    context.stroke();
}

function drawRectangle(valR) {
    context.fillStyle = 'blue';
    context.strokeStyle = 'blue';
    context.globalAlpha = 0.6;
    context.beginPath();
    context.fillRect(width / 2, height / 2, -valR, valR / 2);

}

function drawAXIS() {
    context.strokeStyle = 'black';
    context.fillStyle = 'black';
    context.globalAlpha = 1.0;
    context.beginPath();
    context.moveTo(width / 2, 0);
    context.lineTo(width / 2, height);
    context.stroke();
    context.beginPath();
    context.moveTo(0, height / 2);
    context.lineTo(width, height / 2);
    context.stroke();
    context.strokeText("Y", 240, 10);
    context.strokeText("X", 500, height / 2 - 10);
    context.stroke();
    //draw x-dash
    for (let i = -5; i <= 5; i++) {
        context.beginPath();
        let x = width / 2 + step * i;
        context.moveTo(x, height / 2 + dash);
        context.lineTo(x, height / 2 - dash);
        if (i !== 0) {
            context.fillText(i.toString(), x - dash / 2, height / 2 + 3 * dash);
        }
        context.stroke();
    }

    //draw y-dash
    for (let i = -5; i <= 5; i++) {
        context.beginPath();
        let y = height / 2 + step * i;
        context.moveTo(width / 2 + dash, y);
        context.lineTo(width / 2 - dash, y);
        if (i !== 0) {
            context.fillText((-i).toString(), width / 2 + dash, y + dash);
        }
        context.stroke();
    }
}

function clearCanvas() {
    // Сохраняем текущую матрицу трансформации
    context.save();
// Используем идентичную матрицу трансформации на время очистки
    context.clearRect(0, 0, canvas.width, canvas.height);
// Возобновляем матрицу трансформации
    context.restore();
}

function drawPoints() {
    let rows = [];
    let headers = $(".result_table th");
    console.log(headers)
    $(".result_table tr").each(function (index) {
        let cells = $(this).find("td");
        rows[index] = {};
        cells.each(function (cellIndex) {
            rows[index][$(headers[cellIndex]).html()] = $(this).html().replace(/\s/g, "");
            console.log(rows)
        });
    });
    for (let i = 0; i < rows.length; i++) {
        console.log(rows[i]["X"], rows[i]["Y"], rows[i]["R"])
        drawShoot(rows[i]["X"], rows[i]["Y"], rows[i]["R"])
    }
}

function drawShoot(x, y, r) {
    let canvas = document.getElementById('canvas');
    let chart = canvas.getContext('2d');
    let color;
    if (checkArea(x, y, r) === 'Да') {
        color = 'green';
    } else {
        color = 'red';
    }
    chart.beginPath();
    chart.arc(canvas.width / 2 + x * step, canvas.height / 2 - y * step, dash, 0, Math.PI * 2);
    chart.fillStyle = color;
    chart.strokeStyle = color;
    chart.globalAlpha = 0.45;
    chart.fill();
    chart.stroke();
}

function checkArea(x, y, r) {
    return (checkRectangle(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r)) ? 'Да' : 'Нет';
}

function checkRectangle(x, y, r) {
    return x <= 0 && y <= 0 && x >= -r && y >= -r / 2;
}

function checkTriangle(x, y, r) {
    return x >= 0 && y >= 0 && x + y <= r / 2;
}

function checkCircle(x, y, r) {
    return x <= 0 && y >= 0 && x * x + y * y <= r / 2;
}
