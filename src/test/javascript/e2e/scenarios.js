'use strict';

/* https://docs.angularjs.org/guide/e2e-testing */

describe('Eighty App', function() {
    
    var CONTEXT_PATH = '/eightytest';
    
    var DB_RELOAD_PATH = browser.baseUrl + '/eightytest/db/reload';

    var driver = browser.driver;
    
    var request = require('request');

    var hasClass = function (element, cls) {
        return element.getAttribute('class').then(function (classes) {
            return classes.split(' ').indexOf(cls) !== -1;
        });
    };

    var hover = function (element) {
        browser.actions().mouseMove(element).perform();
    };

    it('should redirect \ to \#\home', function() {
        driver.manage().window().maximize()
        browser.get(CONTEXT_PATH);
        browser.getLocationAbsUrl().then(function(url) {
            expect(url.split('#')[1]).toBe('/home');
        });
    });

    describe('Epic A, Topics', function() {

        beforeEach(function() {
            request.get(DB_RELOAD_PATH);
            browser.get(CONTEXT_PATH);
        });

        it('User views real topics list', function() {
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(2);
        });

        it('User can view topics page', function() {
            element.all(by.css('.abn-tree .tree-icon')).first().click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(3);
            expect(hasClass(element.all(by.css('.abn-tree .abn-tree-row')).first(),'active')).toBe(false);
         });

        it('Should enter edit mode', function() {
            expect($('.add-root-icon').isDisplayed()).not.toBeTruthy();
            $('.edit-toggle').click();
            expect($('.add-root-icon').isDisplayed()).toBeTruthy();
            var firstTopic = element.all(by.css('.abn-tree .tree-label')).first();
            browser.actions().mouseMove(firstTopic).perform();
            var editIcon = element.all(by.css('.abn-tree .icon-file')).first();
            expect(editIcon.isDisplayed()).toBeTruthy();
        });

        it('User can create new top level topic', function() {
            $('.edit-toggle').click();
            $('.add-root-icon').click();
            driver.switchTo().activeElement();
            element(by.model('addTopicInstance.topic.title')).sendKeys('fakeTopic');
            element(by.css('.add-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(3);
        });

        it('User cancels creation new top level topic', function() {
            $('.edit-toggle').click();
            $('.add-root-icon').click();
            driver.switchTo().activeElement();
            element(by.model('addTopicInstance.topic.title')).sendKeys('fakeTopic');
            element(by.css('.cancel-add-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(2);
        });

        it('User can create new sub-topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element.all(by.css('.abn-tree .tree-label')).first();
            browser.actions().mouseMove(firstTopic).perform();
            element.all(by.css('.addFolder')).first().click();
            driver.switchTo().activeElement();
            element(by.model('addTopicInstance.topic.title')).sendKeys('fakeTopic');
            element(by.css('.add-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(4);
        });

        it('User cancels creation new sub-topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element.all(by.css('.abn-tree .tree-label')).first();
            browser.actions().mouseMove(firstTopic).perform();
            element.all(by.css('.addFolder')).first().click();
            driver.switchTo().activeElement();
            element(by.model('addTopicInstance.topic.title')).sendKeys('fakeTopic');
            element(by.css('.cancel-add-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(2);
        });

        it('User can delete top level topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element.all(by.css('.abn-tree .tree-label')).first();
            browser.actions().mouseMove(firstTopic).perform();
            element.all(by.css('.deleteFolder')).first().click();
            driver.switchTo().activeElement();
            element(by.css('.delete-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(1);
        });

        it('User cancels delete top level topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element.all(by.css('.abn-tree .tree-label')).first();
            browser.actions().mouseMove(firstTopic).perform();
            element.all(by.css('.deleteFolder')).first().click();
            driver.switchTo().activeElement();
            element(by.css('.cancel-delete-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(2);
        });

        it('User can delete sub-topic', function() {
            $('.edit-toggle').click();
            element.all(by.css('.abn-tree .tree-icon')).first().click();
            var subTopic = element.all(by.css('.abn-tree .tree-label')).get(1);
            browser.actions().mouseMove(subTopic).perform();
            element.all(by.css('.deleteFolder')).get(1).click();
            driver.switchTo().activeElement();
            element(by.css('.delete-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(2);
        });

        it('User can edit topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element.all(by.css('.abn-tree .tree-label')).first();
            browser.actions().mouseMove(firstTopic).perform();
            element.all(by.css('.editFolder')).first().click();
            driver.switchTo().activeElement();
            firstTopic.getText().then(function(text) {
                var initialTopicTitle = text;
                browser.sleep(10);
                element(by.model('editTopicInstance.topic.title')).sendKeys('fakeTopic');
                element(by.css('.edit-topic')).click();
                expect(firstTopic.getText()).toBe(initialTopicTitle + 'fakeTopic');
            });
        });

        it('User cancels edit topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element.all(by.css('.abn-tree .tree-label')).first();
            browser.actions().mouseMove(firstTopic).perform();
            element.all(by.css('.editFolder')).first().click();
            driver.switchTo().activeElement();
            firstTopic.getText().then(function(text) {
                var initialTopicTitle = text;
                element(by.model('editTopicInstance.topic.title')).sendKeys('fakeTopic');
                element(by.css('.cancel-edit-topic')).click();
                expect(firstTopic.getText()).toBe(initialTopicTitle);
            });
        });
    });

    describe('Epic B, Questions', function() {

        beforeEach(function() {
            request.get(DB_RELOAD_PATH);
            browser.get(CONTEXT_PATH);
        });

        it('View main page', function() {
            var title = element.all(by.css('.div-main')).get(0).getText();
            expect(title).toBe('Main page');
        });

        it('User can view question list for selected list-level topic', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            browser.sleep(10);
            var topicTitle = element(by.css('.topic-title')).getText();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(topicTitle).toBe('Programming Languages');
            expect(setQ.count()).toBe(3);
        });

        it('User can view no question message', function() {
            element.all(by.css('.abn-tree .tree-label')).get(1).click();
            browser.sleep(10);
            var title = element(by.css('.div-error')).getText();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(0);
            expect(title).toBe('No questions');
        });

        it('User can rate question', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            hover(setQ.first());
            element.all(by.css('.rate-up-question')).first().click();
            var postRating = element.all(by.css('.question-rating')).first().getText();
            expect(postRating).toBe('1');
        });

        it('User can create new question', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(3);
            element(by.id('button')).click();
            element(by.model('addQuestion.question.question')).sendKeys('fakeQuestion');
            element(by.css('.add-question')).click();
            setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(4);
        });

        it('User cancels creation new question', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(3);
            element(by.id('button')).click();
            element(by.model('addQuestion.question.question')).sendKeys('fakeQuestion');
            element(by.css('.cancel-add-question')).click();
            expect(setQ.count()).toBe(3);
        });

        it('User can edit a question', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function(initialQuestion) {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                element(by.model('editQuestion.question.question')).sendKeys('fakeQuestion');
                element(by.css('.save-question')).click();
                browser.sleep(10);
                expect(setQ.first().getText()).toBe(initialQuestion + 'fakeQuestion');
            });
        });

        it('User cancel edit a question', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function(initialQuestion) {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                element(by.model('editQuestion.question.question')).sendKeys('fakeQuestion');
                element(by.css('.cancel-save-question')).click();
                browser.sleep(10);
                expect(setQ.first().getText()).toBe(initialQuestion);
            });
        });

        it('User can delete a question', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                browser.sleep(10);
                element(by.css('.delete-question')).click();
                browser.sleep(10);
                element(by.css('.confirm-delete-question')).click();
                browser.sleep(10);
                expect(setQ.count()).toBe(2);
            });
        });

        it('User cancel removal a question', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                browser.sleep(10);
                element(by.css('.delete-question')).click();
                browser.sleep(10);
                element(by.css('.cancel-delete-question')).click();
                browser.sleep(10);
                expect(setQ.count()).toBe(3);
            });
        });
    });

    describe('Epic C, Export', function() {

        beforeEach(function() {
            request.get(DB_RELOAD_PATH);
            browser.get(CONTEXT_PATH);
        });

        it('User can add question to export list', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.export-question')).first().click();
                var elem = element(by.id('export'));
                expect(elem.isDisplayed()).toBe(true);
            });
        });

        it('User see amount of questions to export at the navigation bar', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var text = element(by.id('export-link')).getText();
            expect(text).toBe('selected for export: 1');
        });

        it('Selection to export persists between user visits', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var elem = element.all(by.css('.export-question')).first();
            expect(elem.isDisplayed()).toBe(true);
        });

        it('User can deselect individual questions', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.export-question')).first().click();
                var elem = element(by.id('export'));
                expect(elem.isDisplayed()).toBe(false);
            });
        });

        it('User can clear export list', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.export-question')).first().click();
                var elem = element(by.id('clear-export-list'));
                expect(elem.isDisplayed()).toBe(true);
                elem.click();
                expect(elem.isDisplayed()).toBe(false);
            });
        });

        it('User can browse export list', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.export-question')).first().click();
                element(by.id('export-link')).click();
                browser.getLocationAbsUrl().then(function(url) {
                    expect(url.split('#')[1]).toBe('/export');
                });
            });
        });

        it('User see all the questions selected for export on the Export page', function() {
            element(by.id('export-link')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questionsForExport'));
            expect(setQ.count()).toBe(1);
        });

        it('User see print button', function() {
            element(by.id('export-link')).click();
            var elem = element(by.id('printing'));
            expect(elem.isDisplayed()).toBe(true);
            var elem = element(by.id('clear-export-list'));
            expect(elem.isDisplayed()).toBe(true);
            elem.click();
        });

    });

    describe('Epic D, Tags', function() {

        beforeEach(function() {
            request.get(DB_RELOAD_PATH);
            browser.get(CONTEXT_PATH);
        });

        it('Topic tags displaying', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var elem = element(by.id('div-tags-main'));
            expect(elem.isDisplayed()).toBe(true);
        });

        it('User clicks any tag and sees only questions from selected topic with this tag', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setTags = element.all(by.repeater('tagOrCustomer in questionsCtrl.tagsAndCustomers'));
            browser.sleep(5000);
            expect(setTags.count()).toBe(5);
            var elem = element.all(by.css('.notSelected')).first();
            expect(elem.getAttribute('class')).toBe('badge alert-info ng-binding notSelected');
            elem.click();
            browser.sleep(1000);
            elem = element.all(by.css('.selected')).first();
            expect(elem.getAttribute('class')).toBe('badge alert-info ng-binding selected');
        });

        it('When at least one tag is selected, then only questions with selected tag(s) displayed from selected topic', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(3);
            var elem = element.all(by.css('.notSelected')).first();
            expect(elem.getAttribute('class')).toBe('badge alert-info ng-binding notSelected');
            elem.click();
            browser.sleep(100);
            setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(2);
        });

        it('Popular tags displaying', function() {
            var elem = element(by.id('tagTop_tag1'));
            expect(elem.isDisplayed()).toBe(true);
            elem = element(by.id('tagTop_tag2'));
            expect(elem.isDisplayed()).toBe(true);
        });

        it('When user clicks tag in popular tags, then questions with selected tag displayed', function() {
            element(by.id('tagTop_tag2')).click();
            browser.sleep(100);
            var topicTitle = element(by.css('.topic-title')).getText();
            expect(topicTitle).toBe('Questions with tag: tag2');
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(2);
        });

        it('When user clicks on tag in footer under questions and there are no more questions for this tag, then link to page questionsWithTag is disabled', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            element.all(by.css('.notSelected')).first().click();
            var tag = element.all(by.repeater('selTag in questionsCtrl.selectedTags')).first();
            expect(tag.getAttribute('class')).toBe('ng-scope');
            var elems = element.all(by.css('.disabled'));
            expect(elems.count()).toBe(1);
        });

        it('When user clicks on tag in footer under questions, then all questions with selected tag displayed', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            element(by.id('tag_tag2')).click();
            var tag = element.all(by.repeater('selTag in questionsCtrl.selectedTags')).first();
            expect(tag.getAttribute('class')).toBe('ng-scope');
            var elems = element.all(by.css('.disabled'));
            expect(elems.count()).toBe(0);
            element(by.id('tagSelected_tag2')).click();
            browser.sleep(100);
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(2);
        });

        it('When user clicks on customer in footer under questions and there are no more questions from this customer, then link to page questionsFromCustomer is disabled', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            element.all(by.css('.notSelected')).get(1).click();
            browser.sleep(1000);
            var customer = element.all(by.repeater('selTag in questionsCtrl.selectedTags')).first();
            expect(customer.getAttribute('class')).toBe('ng-scope');
            var elems = element.all(by.css('.disabled'));
            expect(elems.count()).toBe(1);
        });

        it('When user clicks on customer in footer under questions, then all questions from selected customer displayed', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            element(by.id('tag_customer2')).click();
            var tag = element.all(by.repeater('selTag in questionsCtrl.selectedTags')).first();
            expect(tag.getAttribute('class')).toBe('ng-scope');
            var elems = element.all(by.css('.disabled'));
            expect(elems.count()).toBe(0);
            element(by.id('tagSelected_customer2')).click();
            browser.sleep(1000);
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(2);
        });

        it('Customers displaying', function() {
            var elem = element(by.id('customer_customer1'));
            expect(elem.isDisplayed()).toBe(true);
            elem = element(by.id('customer_customer2'));
            expect(elem.isDisplayed()).toBe(true);
        });

        it('When user clicks customer, then questions with selected customer displayed', function() {
            element(by.id('customer_customer1')).click();
            browser.sleep(100);
            var topicTitle = element(by.css('.topic-title')).getText();
            expect(topicTitle).toBe('Questions from customer: customer1');
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(2);
        });

        it('When user switches off all tags button, then all questions from selected topic (and subtopics) are displayed', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(3);
            var elem = element.all(by.css('.notSelected')).first();
            expect(elem.getAttribute('class')).toBe('badge alert-info ng-binding notSelected');
            elem.click();
            browser.sleep(100);
            setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(2);
            var clearElem = element(by.id('clear-tags'));
            clearElem.click();
            browser.sleep(100);
            setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(3);
        });

        it('Add tags when question is being created', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(3);
            element(by.id('button')).click();
            driver.switchTo().activeElement();
            element(by.model('addQuestion.question.question')).sendKeys('fakeQuestion');
            $('#tags div input').sendKeys('newTag1,newTag2,newTag3,');
            element(by.css('.add-question')).click();
            setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(4);
        });

        it('When question is being edited, then user can add/remove/edit both customers and tags', function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function(initialQuestion) {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                element(by.model('editQuestion.question.question')).sendKeys('fakeQuestion');
                $('#tags div input').sendKeys('newTag1,newTag2,newTag3,');
                var tags = element.all(by.repeater('tag in tagList.items track by track(tag)'));
                expect(tags.count()).toBe(5);
                element(by.css('.save-question')).click();
                browser.sleep(10);
                expect(setQ.first().getText()).toBe(initialQuestion + 'fakeQuestion');
                expect(setQ.count()).toBe(3);
            });
        });

        it('Should show/hide "Clear Tag/Tags" button when user selects any tag' , function() {
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var clearTagsBtn = element(by.id('clear-tags'));
            expect(clearTagsBtn.isDisplayed()).toBeFalsy();
            var tagsList = element.all(by.css('.notSelected')); //unselected tags
            tagsList.get(0).click();
            expect(clearTagsBtn.isDisplayed()).toBeTruthy();
            expect(element(by.id('clear-tags')).getText()).toEqual('x');
            element.all(by.css('.selected')).first().click(); //selected tags
            expect(clearTagsBtn.isDisplayed()).toBeFalsy();
            tagsList.get(0).click(); //after first click element with index 0 has been removed from unselected tags list
            tagsList.get(0).click();
            expect(element(by.id('clear-tags')).getText()).toEqual('x');
        });

    });

});
