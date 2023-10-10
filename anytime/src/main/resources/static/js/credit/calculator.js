$().ready(function () {

var $container = $('#container');
	var $menu;
	var _set = { // _set이라는 객체를 생성하고, 그 안에 여러 속성과 값들을 초기화합니다. 
		user: false,
		requiredCredit: 0,
		gradeType: '',
		roundingType: 'round',
		grades: [],
		gradeSelectTemplete: $('<select></select>'),
		reports: [],
		optionsForSemesterPlot: { //왼쪽 차트
			yaxis: { // y축 관련 설정
				min: 0,
				max: 4.5,
				tickColor: '#e3e3e3',
				tickSize: 1,
				tickFormatter: function (i) {
					return i.toFixed(1);
				},
				font: {
					color: '#a6a6a6',
					size: 12
				}
			},
			xaxis: { // x축 관련 설정
				tickColor: 'transparent',
				font: {
					color: '#a6a6a6',
					size: 10
				}
			},
			legend: { // 범례(legend) 관련 설정
				labelBoxBorderColor: 'transparent',
				noColumns: 2,
				labelFormatter: function (label, series) {
					return '<span style="color: ' + series.color + '">' + label + '</span>';
				},
				container: $('article.semester div.series div.legend'),
				sorted: 'reverse'
			},
			series: { // 데이터 시리즈와 관련된 설정
				lines: {
					show: true,
					lineWidth: 1
				},
				points: {
					show: true,
					lineWidth: 2,
					radius: 4
				},
				shadowSize: 0
			},
			grid: { 그리드(grid)와 관련된 설정
				labelMargin: 15,
				borderWidth: 0,
				hoverable: true,
				clickable: true
			},
			colors: ['#a6a6a6', '#c62917'] // 차트의 색상 배열을 지정
		},
		optionsForRatio: { // 오른쪽 차트
			colors: ['#f28572', '#ecc55c', '#a0c661', '#82d1c2', '#7a9ee0']
		}
	};