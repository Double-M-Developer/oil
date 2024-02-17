/* globals Chart:false */

const colorSet = ['#007bff', '#ff007b', 'rgba(119,255,0,0.66)', 'rgba(183,0,255,0.66)'];
const labels = ['고급 휘발유', '휘발유', '경유', 'lpg'];

async function fetchDataAndRender() {
    try {
        await fetchAndRenderChartData();
        await fetchAndRenderRankData();
    } catch (error) {
        console.error('Error fetching data:', error);
        // 여기서 사용자에게 오류 메시지를 보여주는 UI 처리를 할 수 있습니다.
    }
}

async function fetchAndRenderChartData() {
    const response = await fetch('/gas-station/avg');
    const data = await response.json();

    const ctx = document.getElementById('myChart');
    const myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.labels,
            datasets: data.prices.map((priceObj, index) => ({
                label: labels[index],
                lineTension: 0,
                backgroundColor: 'transparent',
                borderColor: colorSet[index],
                pointBackgroundColor: colorSet[index],
                borderWidth: 4,
                data: priceObj.averagePrice,
            })),
        },
        options: getChartOptions(),
    });

    myChart.update();
}

async function fetchAndRenderRankData() {
    const response = await fetch('/gas-station/ranks');
    const rankData = await response.json();

    generateRankTable(rankData);
}

function getChartOptions() {
    return {
        plugins: {
            legend: {
                display: true,
                position: 'bottom',
            },
            tooltip: {
                boxPadding: 3,
            },
        },
    };
}

function generateRankTable(rankData) {
    const tableContainer = document.getElementById('rankTableContainer');
    if (!tableContainer) {
        console.error('Table container element not found!');
        return;
    }

    tableContainer.innerHTML = ''; // 기존 테이블 내용을 클리어

    const table = document.createElement('table');
    table.classList.add('rank-table');

    table.appendChild(createTableHeader(['종류', '1위', '2위', '3위']));
    table.appendChild(createTableBody(rankData));

    tableContainer.appendChild(table);
}

function createTableHeader(headers) {
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    headers.forEach(header => {
        const th = document.createElement('th');
        th.textContent = header;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    return thead;
}

function createTableBody(data) {
    const tbody = document.createElement('tbody');
    Object.entries(data).forEach(([fuelType, brands]) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(fuelType));
        brands.forEach(brand => row.appendChild(createCell(brand)));
        tbody.appendChild(row);
    });
    return tbody;
}

function createCell(text) {
    const cell = document.createElement('td');
    cell.textContent = text;
    return cell;
}

// 페이지 로드 시 차트 데이터 및 순위 테이블 업데이트
fetchDataAndRender();
