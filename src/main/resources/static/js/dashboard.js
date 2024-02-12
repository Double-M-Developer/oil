/* globals Chart:false */
// AJAX 요청을 처리하는 함수
const colorSet = ['#007bff', '#ff007b', '#ff7b00', '#00ff7b']

const fetchChartData = () => {
    fetch('/gas-station/avg') // 서버 엔드포인트 경로
        .then(response => response.json())
        .then(data => {
            // 서버로부터 받은 labels을 차트의 labels로 설정

            console.log(data);
            myChart.data.labels = data.labels;

            // 각 데이터셋에 서버로부터 받은 평균 가격 데이터 할당
            data.prices.forEach((priceObj, index) => {
                if (myChart.data.datasets[index]) {
                    myChart.data.datasets[index].data = priceObj.averagePrice;
                }
            });

            // 차트 업데이트
            myChart.update();
        })
        .catch(error => console.error('Error fetching data:', error));
};


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

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#007bff'
            }, {
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#ff007b',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#ff007b'
            },{
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: 'rgba(119,255,0,0.66)',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: 'rgba(119,255,0,0.66)'
            },{
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: 'rgba(183,0,255,0.66)',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: 'rgba(183,0,255,0.66)'
            }
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