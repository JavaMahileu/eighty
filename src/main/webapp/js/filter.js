(function() {
    /**
     * Created by Aliaksandr_Padalka on 02/07/2014.
     */
    'use strict';

    angular.module('eightyFilters', []);

    /*jshint latedef:false*/
    angular
        .module('eightyFilters')
        .filter('questionFilter', questionFilter);

    function questionFilter() {
        return function(input, searchText) {
            if (!searchText) {
                return input;
            } else {
                var searchRegx = new RegExp(searchText, 'i');
                var result = [];
                for (var i = 0; i < input.length; i++) {
                    if (input[i].question && input[i].question.search(searchRegx) !== -1) {
                        result.push(input[i]);
                    }
                    if ((input[i].answer) && (input[i].answer.search(searchRegx) !== -1) && !(containsInSet(result, input[i]))) {
                        result.push(input[i]);
                    }
                }
                return result;
            }
        };
    }

    angular
        .module('eightyFilters')
        .filter('tagFilter', tagFilter);

    function tagFilter () {
        return function(input, selectedTagsAndCustomers) {
            if (input && selectedTagsAndCustomers && selectedTagsAndCustomers.length > 0) {
                var result = [];
                selectedTagsAndCustomers.forEach(function(tagOrCustomer) {
                    tagOrCustomer.questionsOnPage = 0;
                    input.forEach(function(question) {
                        for(var i = 0; i < question.tags.length; i++) {
                            if ((question.tags[i].tag === tagOrCustomer.tag) && !(containsInSet(result, question))) {
                                result.push(question);
                            }
                        }
                        for(var j = 0; j < question.customers.length; j++) {
                            if ((question.customers[j].name === tagOrCustomer.name) && !(containsInSet(result, question))) {
                                result.push(question);
                            }
                        }
                    });
                });
                return result;
            } else {
                return input;
            }
        };
    }

})();

