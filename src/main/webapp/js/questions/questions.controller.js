(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('topicQuestionsCtrl', topicQuestionsCtrl);

    topicQuestionsCtrl.$inject = ['$scope', 'modalData', 'questionsFactory'];

    function topicQuestionsCtrl($scope, modalData, questionsFactory) {
        /*jshint validthis: true*/
        var vm = this;
        var page = 0;
        var expand = [];

        vm.topic = questionsFactory.getTopic();
        vm.questions = [];
        vm.loading = false;
        vm.shouldBeOpen = modalData.getShouldBeOpen();
        vm.questionsForExport = [];
        vm.selectedTags = [];
        vm.criteria = '';

        vm.loadMore = loadMore;
        vm.clearFilter = clearFilter;
        vm.addQuestion = addQuestion;
        vm.editQuestion = editQuestion;
        vm.rateUp = rateUp;
        vm.exportQuestion = exportQuestion;
        vm.checkCollapsed = checkCollapsed;
        vm.isCollapsedAnswer = isCollapsedAnswer;
        vm.onTagClick = onTagClick;
        vm.isTagSelected = isTagSelected;
        vm.clearTags = clearTags;
        vm.getExportSet = getExportSet;
        vm.checkInSet = checkInSet;

        $scope.$on('topicTags-update', function() {
            questionsFactory.loadTagsAndCustomersByTopic(vm);
        });

        questionsFactory.loadQuestions(vm, page);
        questionsFactory.loadTagsAndCustomersByTopic(vm);

        function loadMore() {
            if (page !== -1) {
                vm.loading = true;
                page++;
                questionsFactory.loadQuestions(vm, page);
            }
        }

        function clearFilter() {
            questionsFactory.clearFilter(vm);
        }

        function addQuestion() {
            questionsFactory.addQuestion(vm);
        }

        function editQuestion(question) {
            getExportSet();
            questionsFactory.editQuestion(question, vm);
        }

        function rateUp(questionUI) {
            questionsFactory.rateUp(questionUI);
        }

        function exportQuestion(question) {
            questionsFactory.exportQuestion(question, vm);
        }

        function checkCollapsed(question) {
            expand = questionsFactory.checkCollapsed(expand, question);
        }

        function isCollapsedAnswer(question) {
            return containsInSet(expand, question);
        }

        function onTagClick(tagOrCustomer) {
            if(angular.isUndefined(vm.selectedTags)) {
                vm.selectedTags = [];
            }
            if(!containsInSet(vm.selectedTags, tagOrCustomer)) {
                tagOrCustomer.questDiff = tagOrCustomer.count - tagOrCustomer.countInTopic;
                vm.selectedTags.push(tagOrCustomer);
            } else {
                vm.selectedTags.splice(getIndex(vm.selectedTags, tagOrCustomer), 1);
            }
        }

        function isTagSelected(tagOrCustomer) {
            if (angular.isUndefined(vm.selectedTags)) {
                return false;
            }
            return containsInSet(vm.selectedTags, tagOrCustomer);
        }

        function clearTags() {
            vm.selectedTags = [];
        }

        function getExportSet() {
            vm.questionsForExport = questionsFactory.getExport();
        }

        function checkInSet(key, obj) {
            return questionsFactory.checkInSet(key, obj);
        }
    }
})();