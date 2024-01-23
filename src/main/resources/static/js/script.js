
    document.addEventListener('DOMContentLoaded', function() {
// 예시 데이터
    const stans = [
{ name: "주유소 A", price: "1500원" },
{ name: "주유소 B", price: "1450원" },
{ name: "주유소 C", price: "1470원" }
    ];

    const container = document.getElementById('gas-station-info');

    stations.forEach(station => {
    const div = document.createElement('div');
    div.classList.add('gas-station');
    div.innerHTML = `<h2>${station.name}</h2><p>가격: ${station.price}</p>`;
    container.appendChild(div);
});
});

