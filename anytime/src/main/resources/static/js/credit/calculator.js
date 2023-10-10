

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

    // _fn 객체 내에 정의된 여러 함수들
	var _fn = {
		initiate: function () { // initiate: 초기화 함수로, 페이지가 로드되면 실행
			$menu = $container.find('div.section > div.menu');
			_set.user = Number($('#userId').val()) ? true : false;
			_set.requiredCredit = Number($('#userRequiredCredit').val());
			_set.gradeType = $('#userGradeType').val();
			_fn.loadReports(); // 함수를 호출하여 보고서 데이터를 로드
			$menu.on('click', 'ol > li', function () {
				var $li = $(this);
				if ($li.data('id')) {
					$li.addClass('active').siblings().removeClass('active');
					_fn.showSubjects($li.data('id'));
				}
				_fn.scrollToActiveMenu();
			});
			$container.find('table.subjects > caption > a.import').on('click', function () { //시간표에 저장되어있던 시간표 불러오기(모달)
				_fn.loadPrimaryTableList();
			});
			$container.find('table.subjects > tbody').on('change', 'input, select', function () { //tbody 바뀌면 보고서 및 과목 정보 업데이트
				_fn.updateReports();
				_fn.createSubjectsInformation();
			});
			$container.find('table.subjects > tfoot a.new').on('click', function () { // 더 입력하기 누를때
				_fn.insertSubject();
			});
			$container.find('table.subjects > tfoot a.reset').on('click', function () { //초기화 버튼 누를
				_fn.resetSubjects();
			});
			$('#importForm').on('submit', function () {
				var $importForm = $(this);
				var id = $importForm.find('select[name="semester"]').val();
				_fn.loadTableLoad(id); // 각 학기별 학점계산표 불러오기 
				$importForm.hide(); //폼($importForm)을 숨김
				return false;
			});
			$container.find('article.overview > div.gpa p.total').on('click', function () { // 학점 타입 변경 작업을 수행
				_fn.changeGradeType();
			});
			$container.find('article.overview > div.acquisition p.total').on('click', function () { //필요한 학점 양식 보여주기 작업을 수행
				_fn.showRequiredCreditForm();
			});
			$('#requiredCreditForm').on('submit', function () { // 본인 졸업에 필요한 학점 설정(모달)
				_fn.saveRequiredCredit();
				return false;
			});
		},
		roundDown: function (number) { // 소수점 아래 두 자리까지 내림한 값을 반환하는 함수
			// return Math.floor(number * 100) / 100; -> 4.35 to 4.34
			return Math.floor(Math.floor(number * 1000) / 10) / 100;
		},
		roundOff: function (number) { // 소수점 아래 두 자리까지 반올림한 값을 반환하는 함수
			// return Math.round(number * 100) / 100; -> 4.475 to 4.47
			return Math.round(Math.floor(number * 1000) / 10) / 100;
		},
		loadReports: function () { //보고서 데이터를 로드하기 위해 서버와 통신하는 AJAX 요청을 보내는 함수
			_fn.ajaxReports(function (data) {
				if (data) _fn.createReports(data);
			});
		},
		ajaxReports: function (callback) {
			var parameters = {};
			if (_set.gradeType !== '') { // _set.gradeType 값이 존재한다면 parameters.grade_type에 할당하여 서버로 전송
				parameters.grade_type = _set.gradeType;
			}
			$.ajax({
				url: _apiServerUrl + '/find/calculator/report/list',
				xhrFields: {withCredentials: true},
				type: 'POST',
				data: parameters,
				success: function (data) { //  처리한 결과 데이터(data)를 AJAX 응답으로 받아와서 콜백 함수 내부에서 사용
					callback(data);
				}
			});
		},
		 
		//  주어진 데이터(data)를 바탕으로 보고서(_set.reports)를 생성하는 역할을 수행 
		createReports: function (data) {
			var $school_id = $(data).find('school').attr('id'); // 데이터에서 'school' 요소를 찾아 그 'id' 속성 값을 $school_id에 저장합니다.
			var $rounding_type = $(data).find('school').attr('roundingType'); //  데이터에서 'school' 요소를 찾아 그 'roundingType' 속성 값을 $rounding_type에 저장합니다.
			var $grades = $(data).find('grades'); // 데이터에서 'grades' 요소를 찾아 $grades에 저장
			var $reports = $(data).find('reports'); // 데이터에서 'reports' 요소를 찾아 $reports에 저장
			_set.roundingType = $rounding_type; // _set.roundingType 변수에 앞서 추출한 반올림 타입($rounding_type)을 할당
			$grades.find('grade').each(function () {
				var $grade = $(this);
				var value = $grade.attr('value');
				_set.grades.push({
					name: $grade.attr('name'),
					value: (isNaN(Number(value)) ? value : Number(value) / 10)
				});
			});
			$reports.find('report').each(function () {
				var $report = $(this);
				var subjects = [];
				$report.find('subject').each(function () {
					var $subject = $(this);
					subjects.push({
						name: $subject.attr('name'),
						credit: Number($subject.attr('credit')),
						grade: $subject.attr('grade'),
						isMajor: $subject.attr('is_major') === 'true'
					});
				});
				_set.reports.push({ // _set.reports 배열에 새로운 보고서 객체를 추가하는 부분
					id: $report.attr('id'),
					semester: $report.attr('semester'),
					credit_calc: Number($report.attr('credit_calc')), // 수강 학점 (평균 계산용, P/NP 제외)
					credit: Number($report.attr('credit')), // 취득 학점 (F, NP 제외)
					sum: Number($report.attr('sum')) / 10, // 취득 성적 합계
					credit_major_calc: Number($report.attr('credit_major_calc')),
					credit_major: Number($report.attr('credit_major')),
					sum_major: Number($report.attr('sum_major')) / 10,
					subjects: subjects,
					school_id: Number($school_id)
				});
			});
			_fn.setGradeSelectTemplete();
			_fn.createSemesterList();
			setTimeout(function () { // 약간의 지연(100밀리초 = 0.1초) 후 개요 및 학기 정보 생성 함수 호출
				_fn.createOverview();
				_fn.createSemester();
			}, 100);
		},
		
		//  웹 페이지 상에서 사용자가 과목 정보(성적, 학점 등)를 수정했을 때 그 변경사항을 실제 보고서 데이터에 반영하고, 이에 따라 필요한 부분들(개요, 학기 정보 등)을 업데이트하는 역할을 수행
		updateReports: function () {
			var $subjects = $container.find('table.subjects');
			var $tbody = $subjects.find('tbody');
			// eslint-disable-next-line object-curly-spacing
			var report = _.findWhere(_set.reports, { id: $tbody.data('id') });
			report.subjects = [];
			$tbody.find('tr').each(function () {
				var $tr = $(this);
				var credit = Number($tr.find('input[name="credit"]').val());
				if (credit < 0) {
					$tr.find('input[name="credit"]').val(0);
					credit = 0;
				}
				report.subjects.push({
					grade: $tr.find('select[name="grade"]').val(),
					credit: credit,
					name: $tr.find('input[name="name"]').val(),
					isMajor: $tr.find('input[name="major"]').prop('checked')
				});
			});
			var majorSubjects = _.where(report.subjects, {isMajor: true});
			var filterPassSubjects = function (subjects) { // 주어진 과목 리스트에서 성적이 F 또는 NP가 아닌 과목만 추출
				return _.reject(subjects, function (subject) {
					return _.contains(['F', 'NP'], subject.grade);
				});
			};
			var filterCalcSubjects = function (subjects) { // 주어진 과목 리스트에서 성적이 P 또는 NP가 아닌 과목만 추출  
				return _.reject(subjects, function (subject) {
					return _.contains(['P', 'NP'], subject.grade);
				});
			};
			var sumCredits = function (subjects) { // 주어진 과목 리스트에서 각각의 학점(credit) 값을 합산
				return _.reduce(subjects, function (mem, subject) {
					return mem + subject.credit;
				}, 0);
			};
			var sumGrades = function (subjects) { // 주어진 과목 리스트에서 각각의 (학점 x 해당 성적 점수) 값을 합산
				return _.reduce(subjects, function (mem, subject) {
					// eslint-disable-next-line object-curly-spacing
					return mem + subject.credit * _.findWhere(_set.grades, { name: subject.grade }).value;
				}, 0);
			};
			report.credit = sumCredits(filterPassSubjects(report.subjects));
			report.credit_calc = sumCredits(filterCalcSubjects(report.subjects));
			report.sum = sumGrades(filterCalcSubjects(report.subjects));
			report.credit_major = sumCredits(filterPassSubjects(majorSubjects));
			report.credit_major_calc = sumCredits(filterCalcSubjects(majorSubjects));
			report.sum_major = sumGrades(filterCalcSubjects(majorSubjects));
			_fn.saveReports();
			_fn.createOverview();
			_fn.createSemester();
		},
		
		// 보고서(_set.reports)를 서버에 저장하는 역할을 수행
		saveReports: function () {
			if (!_set.user) {
				return false;
			}
			var $subjects = $container.find('table.subjects');
			var $tbody = $subjects.find('tbody');
			// eslint-disable-next-line object-curly-spacing
			var report = _.findWhere(_set.reports, { id: $tbody.data('id') });
			var data = {
				id: report.id,
				sum: report.sum * 10,
				credit: report.credit,
				credit_calc: report.credit_calc,
				sum_major: report.sum_major * 10,
				credit_major: report.credit_major,
				credit_major_calc: report.credit_major_calc,
				names: _.pluck(report.subjects, 'name'),
				grades: _.pluck(report.subjects, 'grade'),
				credits: _.pluck(report.subjects, 'credit'),
				// eslint-disable-next-line comma-dangle
				is_majors: _.pluck(report.subjects, 'isMajor'),
			};
			$.ajax({
				url: _apiServerUrl + '/save/calculator/report/list',
				xhrFields: {withCredentials: true},
				type: 'POST',
				data: data
			});
		},
		// 웹 페이지 상에서 사용자가 과목별 성적을 선택할 수 있는 드롭다운 메뉴(선택 박스; select box)의 옵션들(option elements)을 동적으로 생성하는 역할을 수행
		setGradeSelectTemplete: function () {
			_.each(_set.grades, function (grade) {
				$('<option></option>').val(grade.name).text(grade.name).appendTo(_set.gradeSelectTemplete);
			});
		},
		
		// 학기 목록을 생성하는 역할을 수행
		createSemesterList: function () {
			var $ol = $('<ol></ol>');
			_.each(_set.reports, function (report) {
				var $li;
				$li = $('<li></li>').data('id', report.id).appendTo($ol);
				$('<a></a>').text(report.semester).appendTo($li);
			});
			$ol.appendTo($menu);
			var lastReport = _.chain(_set.reports).sortBy(function (report) {
				return -report.id;
			}).find(function (report) {
				return report.credit_calc > 0;
			}).value();
			if (typeof lastReport !== 'undefined') {
				$ol.find('li').filter(function () {
					return $(this).data('id') === lastReport.id;
				}).click();
			} else {
				$ol.find('li').filter(function () {
					return $(this).data('id');
				}).first().click();
			}
		},
		
		 // 이 함수는 웹 페이지 상에서 학기별 평균 점수(GPA), 전공 점수, 취득 학점 등 전반적인 개요 정보 및 점수 분포도(비율 그래프) 등을 동적으로 생성하고 업데이트하는 역할을 수행
		createOverview: function () {
			var $gpa = $container.find('article.overview div.gpa');
			var $major = $container.find('article.overview div.major');
			var $acquisition = $container.find('article.overview div.acquisition');
			var $ratioplot = $container.find('article.semester ul.ratioplot');
			var data = _fn.calculateOverview();
			$gpa.find('p.value').text(data.gpa);
			$gpa.find('p.total').text('/ ' + data.perfectGrade);
			if (_set.gradeType !== '') {
				$('<span></span>').text('변경').addClass('button').appendTo($gpa.find('p.total'));
			}
			$major.find('p.value').text(data.gpaMajor);
			$major.find('p.total').text('/ ' + data.perfectGrade);
			$acquisition.find('p.value').text(data.acquiredCredit);
			$acquisition.find('p.total').text('/ ' + data.requiredCredit);
			$ratioplot.empty();
			if (data.ratio.length) {
				var firstRatio = _.first(data.ratio).ratio;
				var adjustmentRatio = 1 / ((Math.round(firstRatio * 10) / 10) + .2);
				_.each(data.ratio.slice(0, 5), function (row, index) {
					var grade = row.grade;
					var ratio = row.ratio;
					var color = _set.optionsForRatio.colors[index];
					var width = 'calc(' + (row.ratio * adjustmentRatio * 100) + '%)';
					$ratioplot.append(
						$('<li>').append(
							$('<span>').text(grade).addClass('grade'),
							$('<div>').addClass('ratiowrapper').append(
								// eslint-disable-next-line object-curly-spacing
								$('<div>').addClass('ratiobar').css({ width: width, height: '4px', backgroundColor: color }),
								// eslint-disable-next-line object-curly-spacing
								$('<span>').text(Math.round(ratio * 100) + '%').addClass('ratiotext').css({ left: width, color: color })
							)
						)
					);
				});
			}
		},
		
		// 학점계산과 관련된 여러 값을 계산하고, 그 결과를 객체 형태로 반환하는 역할
		calculateOverview: function () {
			var acquiredCredit = _.reduce(_set.reports, function (memory, report) { // 취득한 총 학점
				return memory + report.credit;
			}, 0);
			var takenCredit = _.reduce(_set.reports, function (memory, report) { // 수강한 총 학점
				return memory + report.credit_calc;
			}, 0);
			var takenCreditMajor = _.reduce(_set.reports, function (memory, report) { // 전공에서 수강한 총 학점을 의미
				return memory + report.credit_major_calc;
			}, 0);
			// eslint-disable-next-line object-curly-spacing
			var perfectGrade = (_.findWhere(_set.grades, { name: 'A+' }) || _.findWhere(_set.grades, { name: 'A0' })).value; // 학점 기준 (4.3 or 4.5 or 4.0)
			var gpa = 0;
			if (takenCredit > 0) {
				var totalPoint = _.reduce(_set.reports, function (memory, report) {
					return memory + report.sum;
				}, 0);
				gpa = totalPoint / takenCredit;
				if (_set.roundingType === 'floor') {
					gpa = _fn.roundDown(gpa);
				} else if (_set.roundingType === 'round') {
					gpa = _fn.roundOff(gpa);
				}
			}
			var gpaMajor = 0;
			if (takenCreditMajor > 0) {
				var totalMajorPoint = _.reduce(_set.reports, function (memory, report) {
					return memory + report.sum_major;
				}, 0);
				gpaMajor = totalMajorPoint / takenCreditMajor;
				if (_set.roundingType === 'floor') {
					gpaMajor = _fn.roundDown(gpaMajor);
				} else if (_set.roundingType === 'round') {
					gpaMajor = _fn.roundOff(gpaMajor);
				}
			}

			var grades = _.chain(_set.reports)
				.map(function (report) {
					return _.pluck(
						_.filter(report.subjects, function (subject) {
							return subject.credit > 0;
						}),
						'grade'
					);
				})
				.flatten()
				.value();
			var sum = grades.length;
			var ratio = _.chain(grades)
				.countBy()
				.reduce(function (result, count, grade) {
					result.push({
						grade: grade,
						count: count,
						ratio: count / sum
					});
					return result;
				}, [])
				.sort(function (a, b) {
					return b.ratio - a.ratio;
				})
				.value();

			return {
				requiredCredit: _set.requiredCredit,
				acquiredCredit: acquiredCredit,
				perfectGrade: perfectGrade,
				gpa: gpa,
				gpaMajor: gpaMajor,
				ratio: ratio
			};
		},
		createSemester: function () {
			var $chart = $container.find('div.chart');
			var $semester = $chart.find('article.semester');
			var $series = $semester.find('div.series');
			var data = _fn.calculateSemester();
			if (data.length) {
				$chart.removeClass('empty');
				var dataForPlot = [
					/* eslint-disable object-curly-spacing */
					{ label: '전공', data: _.pluck(data, 'reportsMajor') },
					{ label: '전체', data: _.pluck(data, 'reports') }
					/* eslint-enable object-curly-spacing */
				];
				_set.optionsForSemesterPlot.xaxis.ticks = data.length - 1;
				_set.optionsForSemesterPlot.xaxis.tickFormatter = function (i) {
					return data[i].semester.replace(' ', '<br>');
				};
				var reportsMajors = _.pluck(data, 'reportsMajor');
				var reports = _.pluck(data, 'reports');
				var minScoreList = reports
					.concat(reportsMajors)
					.map(function (report) {
						return report[1];
					}).filter(function (score) {
						return !isNaN(score);
					});

				var minScore = minScoreList.reduce(function (a, b) {
					return Math.min(a, b);
				});

				if (minScore >= 1.5) {
					_set.optionsForSemesterPlot.yaxis.min = 1.5;
				} else if (minScore >= 0.5) {
					_set.optionsForSemesterPlot.yaxis.min = 0.5;
				} else {
					_set.optionsForSemesterPlot.yaxis.min = 0;
				}

				$series.find('div.plot').plot(dataForPlot, _set.optionsForSemesterPlot).off('plothover').on('plothover', function (event, pos, item) {
					$('div.plottooltip').remove();
					if (!item) return false;
					var $tooltip = $('<div></div>').text(item.datapoint[1]).addClass('plottooltip').appendTo('body');
					$tooltip.css({
						left: item.pageX - $tooltip.outerWidth() / 2,
						top: item.pageY + 22,
						color: item.series.color
					});
				}).resize();
			} else {
				$chart.addClass('empty');
			}
		},
		calculateSemester: function () {
			var reports = _.reject(_set.reports, function (report) {
				return report.credit_calc === 0 || report.semester.match(/(여름학기|겨울학기|기타 학기)/) ;
			});
			return _.map(reports, function (report, index) {
				var gpa = report.sum / report.credit_calc;
				var gpaMajor = undefined;
				if (report.credit_major_calc > 0) {
					gpaMajor = report.sum_major / report.credit_major_calc;
				}
				if (_set.roundingType === 'floor') {
					gpa = _fn.roundDown(gpa);
					gpaMajor = _fn.roundDown(gpaMajor);
				} else if (_set.roundingType === 'round') {
					gpa = _fn.roundOff(gpa);
					gpaMajor = _fn.roundOff(gpaMajor);
				}
				return {
					id: report.id,
					semester: report.semester,
					reports: [index, gpa],
					reportsMajor: [index, gpaMajor]
				};
			});
		},
		showSubjects: function (id) {
			var $subjects = $container.find('table.subjects');
			var $caption = $subjects.find('caption');
			var $tbody = $subjects.find('tbody');
			// eslint-disable-next-line object-curly-spacing
			var report = _.findWhere(_set.reports, { id: id });
			$caption.find('h3').text(report.semester);
			$tbody.empty().data('id', report.id);
			_.each(report.subjects, function (subject) {
				_fn.insertSubject(subject);
			});
			_fn.createSubjectsInformation();
		},
		insertSubject: function (subject) {
			var $tbody = $container.find('table.subjects > tbody');
			var $tbodyTr = $('<tr></tr>').appendTo($tbody);
			var $nameTd = $('<td></td>').appendTo($tbodyTr);
			var $creditTd = $('<td></td>').appendTo($tbodyTr);
			var $gradeTd = $('<td></td>').appendTo($tbodyTr);
			var $majorTd = $('<td></td>').appendTo($tbodyTr);
			if (!subject) {
				subject = {
					name: '',
					credit: 0,
					grade: 'A+',
					isMajor: false
				};
			}
			var $gradeSelect = _set.gradeSelectTemplete.clone();
			$gradeSelect.find('option').filter(function () {
				return $(this).text() === subject.grade;
			}).attr('selected', 'selected');
			$gradeSelect.attr('name', 'grade').appendTo($gradeTd);
			$('<input>').attr('name', 'credit').val(subject.credit).attr({
				type: 'number',
				maxlength: '4',
				min: 0
			}).appendTo($creditTd);
			$('<input>').attr('name', 'name').val(subject.name).attr({
				maxlength: '50'
			}).appendTo($nameTd);
			$('<label>').appendTo($majorTd).append(
				// eslint-disable-next-line object-curly-spacing
				$('<input>').attr({ name: 'major', type: 'checkbox' }).prop({
					checked: subject.isMajor
				}),
				$('<span>')
			);
		},
		resetSubjects: function () {
			var $subjects = $container.find('table.subjects');
			var $tbody = $subjects.find('tbody');
			if (!confirm($subjects.find('caption > h3').text() + ' 정보를 초기화하시겠습니까?')) {
				return false;
			}
			$tbody.find('tr:eq(9)').nextAll().remove();
			$tbody.find('select[name="grade"]').val('A+');
			$tbody.find('input[name="credit"]').val('0');
			$tbody.find('input[name="name"]').val('');
			// eslint-disable-next-line object-curly-spacing
			$tbody.find('input[name="major"]').prop({ checked: false });
			$tbody.find('input').first().change();
		},
		createSubjectsInformation: function () {
			var $subjects = $container.find('table.subjects');
			var $caption = $subjects.find('caption');
			var $tbody = $subjects.find('tbody');
			// eslint-disable-next-line object-curly-spacing
			var report = _.findWhere(_set.reports, { id: $tbody.data('id') });
			var gpa = 0;
			if (report.credit_calc > 0) {
				gpa = report.sum / report.credit_calc;
				if (_set.roundingType === 'floor') {
					gpa = _fn.roundDown(gpa);
				} else if (_set.roundingType === 'round') {
					gpa = _fn.roundOff(gpa);
				}
			}
			var gpaMajor = 0;
			if (report.credit_major_calc > 0) {
				gpaMajor = report.sum_major / report.credit_major_calc;
				if (_set.roundingType === 'floor') {
					gpaMajor = _fn.roundDown(gpaMajor);
				} else if (_set.roundingType === 'round') {
					gpaMajor = _fn.roundOff(gpaMajor);
				}
			}
			var acquisition = report.credit;
			$caption.find('dl.information > dd.gpa').text(gpa);
			$caption.find('dl.information > dd.major').text(gpaMajor);
			$caption.find('dl.information > dd.acquisition').text(acquisition);
			if (_set.user) {
				$caption.find('a.import').show();
			}
		},
		
		loadPrimaryTableList: function () {
			var $importForm = $('#importForm');
			var $select = $importForm.find('select[name="semester"]');
			if ($select.is(':empty')) {
				_fn.ajaxPrimaryTableList(function (data) {
					if (!$(data).find('table').length) {
						alert('가져올 수 있는 시간표가 없습니다.\n먼저 시간표를 만들어보세요!');
						return false;
					}
					$(data).find('table').each(function () {
						var $table = $(this);
						$('<option></option>').val($table.attr('id')).text($table.attr('year') + '년 ' + $table.attr('semester') + '학기 (' + $table.attr('name') + ')').appendTo($select);
					});
					_fn.showImportForm();
				});
			} else {
				_fn.showImportForm();
			}
		},
		ajaxPrimaryTableList: function (callback) {
			if (!_set.user) {
				callback();
				return false;
			}
			$.ajax({
				url: _apiServerUrl + '/find/timetable/table/list/primary',
				xhrFields: {withCredentials: true},
				type: 'POST',
				success: function (data) {
					callback(data);
				}
			});
		},
		showImportForm: function () {
			var $importForm = $('#importForm');
			$importForm.show();
			$importForm.find('a.close').one('click', function () {
				$importForm.hide();
			});
		},
		loadTableLoad: function (id) {
			_fn.ajaxTableLoad(id, function (data) {
				if (data) _fn.applyTableLoad(data);
			});
		},
		ajaxTableLoad: function (id, callback) {
			if (!_set.user) {
				callback();
				return false;
			}
			$.ajax({
				url: _apiServerUrl + '/find/timetable/table',
				xhrFields: {withCredentials: true},
				data: {
					id: id
				},
				type: 'POST',
				success: function (data) {
					var responseCode;
					if (!$(data).find('response').children().length) {
						responseCode = $(data).find('response').text();
					}
					if (responseCode === '-1' || responseCode === '-2') {
						callback();
					} else {
						callback(data);
					}
				}
			});
		},
		applyTableLoad: function (data) {
			var $subjects = $container.find('table.subjects');
			var $tbody = $subjects.find('tbody');
			$tbody.find('select[name="grade"]').val('A+');
			$tbody.find('input[name="credit"]').val('');
			$tbody.find('input[name="name"]').val('');
			// eslint-disable-next-line object-curly-spacing
			$tbody.find('input[name="major"]').prop({ checked: false });
			$(data).find('subject').each(function (index) {
				var $subject = $(this);
				var $tbodyTr = $tbody.find('tr').eq(index);
				var credit = $subject.find('credit').attr('value');
				if (!credit) credit = 0;
				var name = $subject.find('name').attr('value');
				$tbodyTr.find('input[name="credit"]').val(credit);
				$tbodyTr.find('input[name="name"]').val(name);
			});
			$tbody.find('input').first().change();
		},
		changeGradeType: function () {
			if (_set.gradeType === '') {
				return false;
			}
			if (!confirm('만점 기준을 변경 시 입력하신 정보가 초기화됩니다.')) {
				return false;
			}
			if (_set.gradeType === '0') {
				location.href = '?grade_type=1';
			} else {
				location.href = '?grade_type=0';
			}
		},
		showRequiredCreditForm: function () {
			var $requiredCreditForm = $('#requiredCreditForm');
			$requiredCreditForm.find('input[name="required_credit"]').val(_set.requiredCredit);
			$requiredCreditForm.show();
			$requiredCreditForm.find('a.close').one('click', function () {
				$requiredCreditForm.hide();
			});
		},
		saveRequiredCredit: function () {
			var $requiredCreditForm = $('#requiredCreditForm');
			var $requiredCredit = $requiredCreditForm.find('input[name="required_credit"]');
			if (!$requiredCredit.val().replace(/ /gi, '') || isNaN(Number($requiredCredit.val())) || !Number($requiredCredit.val())) {
				alert('정확한 졸업 학점을 입력하세요!');
				$requiredCredit.focus();
				return false;
			}
			_set.requiredCredit = Number($requiredCredit.val());
			_fn.createOverview();
			$requiredCreditForm.hide();
			if (!_set.user) {
				return false;
			}
			$.ajax({
				url: _apiServerUrl + '/update/user/requiredCredit',
				xhrFields: {withCredentials: true},
				type: 'POST',
				data: {
					required_credit: $requiredCredit.val()
				}
			});
		},
		scrollToActiveMenu: function () {
			if (!$menu.is(':has(li.active)')) {
				return false;
			}
			$menu.scrollLeft(0);
			var left = Math.floor($menu.find('li.active').position().left) - 50;
			$menu.scrollLeft(left);
		}
	};
	_fn.initiate();
});
