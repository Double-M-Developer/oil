/* globals Chart:false */
// AJAX 요청을 처리하는 함수
const colorSet = ['#007bff', '#ff007b', '#ff7b00', '#00ff7b']
const fetchChartData = () => {
    fetch('/path-to-data-endpoint') // 서버 엔드포인트 경로
        .then(response => response.json())
        .then(data => {
            // 차트 데이터셋 업데이트
            myChart.data.labels = data.labels;
            data.forEach((priceList, index) => {
                // 각 데이터셋에 새로운 데이터 할당
                // 전국 평균, 시도별 평균
                if (myChart.data.datasets[index]) {
                    myChart.data.datasets[index].data = priceList;
                    myChart.data.datasets[index].borderColor = colorSet[index];
                    myChart.data.datasets[index].pointBackgroundColor = colorSet[index];
                }
            })
            myChart.update();
        })
        .catch(error => console.error('Error fetching data:', error));
}
// (() => {
// 'use strict'

// Graphs
const ctx = document.getElementById('myChart')
// eslint-disable-next-line no-unused-vars
const myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [
            'Sunday',
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday',
            'Saturday'
        ],
        datasets: [
            {
                data: [
                    15339,
                    21345,
                    18483,
                    24003,
                    23489,
                    24092,
                    12034
                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#007bff'
            }, {
                data: [
                    21300,
                    15339,
                    21345,
                    12034,
                    13489,
                    14003,
                    16092
                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#ff007b',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#ff007b'
            },
        ]
    },
    options: {
        plugins: {
            legend: {
                display: false
            },
            tooltip: {
                boxPadding: 3
            }
        }
    }
});
// })()

// 페이지 로드 시 차트 데이터 업데이트
fetchChartData();