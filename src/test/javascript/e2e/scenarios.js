'use strict';

/* https://docs.angularjs.org/guide/e2e-testing */

describe('Eighty App', function() {
    
    var CONTEXT_PATH = '/eighty';
    
    var DB_RELOAD_PATH = browser.baseUrl + CONTEXT_PATH + '/db/reload';

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
            expect(rootTopics.count()).toBe(3);
        });

        it('User can view topics page', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).element(by.xpath('parent::div/span[contains(@class,"tree-icon")]')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(4);
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
            expect(rootTopics.count()).toBe(4);
        });

        it('User cancels creation new top level topic', function() {
            $('.edit-toggle').click();
            $('.add-root-icon').click();
            driver.switchTo().activeElement();
            element(by.model('addTopicInstance.topic.title')).sendKeys('fakeTopic');
            element(by.css('.cancel-add-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(3);
        });

        it('User can create new sub-topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages'));
            hover(firstTopic);
            var addTopic = firstTopic.element(by.xpath('parent::div/span/i[contains(@class,"addFolder")]')).click();
            //browser.driver.wait(protractor.until.elementIsNotVisible(addTopic), 5000);
            driver.switchTo().activeElement();
            element(by.model('addTopicInstance.topic.title')).sendKeys('fakeTopic');
            element(by.css('.add-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(5);
        });

        it('User cancels creation new sub-topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages'));
            hover(firstTopic);
            firstTopic.element(by.xpath('parent::div/span/i[contains(@class,"addFolder")]')).click();
            driver.switchTo().activeElement();
            element(by.model('addTopicInstance.topic.title')).sendKeys('fakeTopic');
            element(by.css('.cancel-add-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(3);
        });

        it('User can delete top level topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages'));
            hover(firstTopic);
            firstTopic.element(by.xpath('parent::div/span/i[contains(@class,"deleteFolder")]')).click();
            driver.switchTo().activeElement();
            element(by.css('.delete-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(2);
        });

        it('User cancels delete top level topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages'));
            hover(firstTopic);
            firstTopic.element(by.xpath('parent::div/span/i[contains(@class,"deleteFolder")]')).click();
            driver.switchTo().activeElement();
            element(by.css('.cancel-delete-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(3);
        });

        it('User can delete sub-topic', function() {
            $('.edit-toggle').click();
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).element(by.xpath('parent::div/span[contains(@class,"tree-icon")]')).click();
            var subTopic = element(by.cssContainingText('.abn-tree .tree-label', 'Java'));
            hover(subTopic);
            subTopic.element(by.xpath('parent::div/span/i[contains(@class,"deleteFolder")]')).click();
            driver.switchTo().activeElement();
            element(by.css('.delete-topic')).click();
            var rootTopics = element.all(by.repeater('row in tree_rows | filter:{visible:true} track by row.branch.uid'));
            expect(rootTopics.count()).toBe(3);
        });

        it('User can edit topic', function() {
            $('.edit-toggle').click();
            var firstTopic = element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages'));
            hover(firstTopic);
            firstTopic.element(by.xpath('parent::div/span/i[contains(@class,"editFolder")]')).click();
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
            var firstTopic = element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages'));
            hover(firstTopic);
            firstTopic.element(by.xpath('parent::div/span/i[contains(@class,"editFolder")]')).click();
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
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));           
            expect(setQ.count()).toBe(6);
        });

        it('User can view no question message', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Empty topic')).click();
            var title = element(by.css('.div-error')).getText();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(0);
            expect(title).toBe('No questions');
        });

        it('User can rate question', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            hover(setQ.first());
            element.all(by.css('.rate-up-question')).first().click();
            var postRating = element.all(by.css('.question-rating')).first().getText();
            expect(postRating).toBe('1');
        });

        it('User can create new question', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(6);
            element(by.id('button')).click();
            element(by.model('addQuestion.question.question')).sendKeys('fakeQuestion');
            element(by.css('.add-question')).click();
            setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(7);
        });

        it('User cancels creation new question', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(6);
            element(by.id('button')).click();
            element(by.model('addQuestion.question.question')).sendKeys('fakeQuestion');
            element(by.css('.cancel-add-question')).click();
            expect(setQ.count()).toBe(6);
        });

        it('User can edit a question', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
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
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
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
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                browser.sleep(10);
                element(by.css('.delete-question')).click();
                browser.sleep(10);
                element(by.css('.confirm-delete-question')).click();
                browser.sleep(10);
                expect(setQ.count()).toBe(5);
            });
        });

        it('User cancel removal a question', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                browser.sleep(10);
                element(by.css('.delete-question')).click();
                browser.sleep(10);
                element(by.css('.cancel-delete-question')).click();
                browser.sleep(10);
                expect(setQ.count()).toBe(6);
            });
        });
    });

    describe('Epic C, Export', function() {

        beforeEach(function() {
            request.get(DB_RELOAD_PATH);
            browser.get(CONTEXT_PATH);
        });
        
        afterEach(function() {
            var elem = element(by.id('clear-export-list'));
            elem.isDisplayed().then(function (isVisible) {
                if (isVisible) {
                    elem.click();
                }
            });
        });

        it('User can add question to export list', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.export-question')).first().click();
                var elem = element(by.id('export'));
                expect(elem.isDisplayed()).toBe(true);
            });
        });

        it('User see amount of questions to export at the navigation bar', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.export-question')).first().click();
                var elem = element(by.id('export'));
                expect(elem.isDisplayed()).toBe(true);
            });
            
            element.all(by.css('.abn-tree .tree-label')).first().click();
            var text = element(by.id('export-link')).getText();
            expect(text).toBe('selected for export: 1');
        });

        it('Selection to export persists between user visits', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                hover(setQ.first());
                element.all(by.css('.export-question')).first().click();
                var elem = element(by.id('export'));
                expect(elem.isDisplayed()).toBe(true);
            });
            
            browser.get(CONTEXT_PATH);
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var elem = element.all(by.css('.export-question')).first();
            expect(elem.isDisplayed()).toBe(true);
        });

        it('User can deselect individual questions', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function() {
                var question = setQ.first();
                hover(question);
                question.element(by.css('.export-question')).click();
                question.element(by.css('.export-question')).click();
                var elem = element(by.id('export'));
                expect(elem.isDisplayed()).toBe(false);
            });
        });

        it('User can clear export list', function() {           
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
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
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
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
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            hover(setQ.first());
            element.all(by.css('.export-question')).first().click();
            var elem = element(by.id('export'));
            expect(elem.isDisplayed()).toBe(true);
            
            element(by.id('export-link')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questionsForExport'));
            expect(setQ.count()).toBe(1); 
        });

        it('User see print button', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            hover(setQ.first());
            element.all(by.css('.export-question')).first().click();
            var elem = element(by.id('export'));
            expect(elem.isDisplayed()).toBe(true);
            
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
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var elem = element(by.id('div-tags-main'));
            expect(elem.isDisplayed()).toBe(true);
        });

        it('User clicks any tag and sees only questions from selected topic with this tag', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(6);
            var setTags = element.all(by.repeater('tagOrCustomer in questionsCtrl.tagsAndCustomers'));
            expect(setTags.count()).toBe(11);
            var tag = element(by.id('tag_object'));
            tag.click();
            expect(setQ.count()).toBe(2);
        });

        it('When at least one tag is selected, then only questions with selected tag(s) displayed from selected topic', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(6);
            var setTags = element.all(by.repeater('tagOrCustomer in questionsCtrl.tagsAndCustomers'));
            expect(setTags.count()).toBe(11);
            var tag = element(by.id('tag_object'));
            tag.click();
            expect(setQ.count()).toBe(2);
        });

        it('Popular tags displaying', function() {
            var elem = element(by.id('tagTop_class'));
            expect(elem.isDisplayed()).toBe(true);
            elem = element(by.id('tagTop_variable'));
            expect(elem.isDisplayed()).toBe(true);
        });

        it('When user clicks tag in popular tags, then questions with selected tag displayed', function() {
            element(by.id('tagTop_class')).click();
            var topicTitle = element(by.css('.topic-title')).getText();
            expect(topicTitle).toBe('Questions with tag: class');
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(2);
        });

        it('When user clicks on tag in footer under questions and there are no more questions for this tag, then link to page questionsWithTag is disabled', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            browser.sleep(10);
            element(by.id('tag_class')).click();
            var link = element(by.id('tagSelected_class'));
            expect(link.isDisplayed()).toBe(true);
        });

        it('When user clicks on tag in footer under questions, then all questions with selected tag displayed', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            browser.sleep(10);
            element(by.id('tag_object')).click();
            browser.sleep(10);
            element(by.id('tagSelected_object')).click();
            browser.sleep(10);
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(3);
        });

        it('When user clicks on customer in footer under questions and there are no more questions from this customer, then link to page questionsFromCustomer is disabled', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            browser.sleep(10);
            element(by.id('tag_Customer1')).click();
            var link = element(by.id('tagSelected_Customer1'));
            expect(link.isDisplayed()).toBe(true);
        });

        it('When user clicks on customer in footer under questions, then all questions from selected customer displayed', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            browser.sleep(10);
            element(by.id('tag_Customer2')).click();
            browser.sleep(10);
            element(by.id('tagSelected_Customer2')).click();
            browser.sleep(10);
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(3);
        });

        it('Customers displaying', function() {
            var elem = element(by.id('customer_Customer1'));
            expect(elem.isDisplayed()).toBe(true);
            elem = element(by.id('customer_Customer2'));
            expect(elem.isDisplayed()).toBe(true);
        });

        it('When user clicks customer, then questions with selected customer displayed', function() {
            element(by.id('customer_Customer1')).click();
            browser.sleep(10);
            var topicTitle = element(by.css('.topic-title')).getText();
            expect(topicTitle).toBe('Questions from customer: Customer1');
            var setQ = element.all(by.repeater('quest in filtered.questions | questionFilter : filtered.criteria track by $index'));
            expect(setQ.count()).toBe(2);
        });

        it('When user switches off all tags button, then all questions from selected topic (and subtopics) are displayed', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(6);
            element(by.id('tag_object')).click();
            element(by.id('tag_Customer1')).click();
            browser.sleep(10);
            expect(setQ.count()).toBe(3);
            element(by.id('clear-tags')).click();
            browser.sleep(10);
            expect(setQ.count()).toBe(6);
        });

        it('Add tags when question is being created', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(6);
            element(by.id('button')).click();
            driver.switchTo().activeElement();
            element(by.model('addQuestion.question.question')).sendKeys('fakeQuestion');
            $('#tags div input').sendKeys('newTag1,newTag2,newTag3,');
            element(by.css('.add-question')).click();
            setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            expect(setQ.count()).toBe(7);
        });

        it('When question is being edited, then user can add/remove/edit both customers and tags', function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
            var setQ = element.all(by.repeater('quest in questionsCtrl.questions| questionFilter : questionsCtrl.criteria | tagFilter : questionsCtrl.selectedTags track by $index'));
            setQ.first().getText().then(function(initialQuestion) {
                hover(setQ.first());
                element.all(by.css('.edit-question')).first().click();
                element(by.model('editQuestion.question.question')).sendKeys('fakeQuestion');
                $('#tags div input').sendKeys('newTag1,newTag2,newTag3,');
                var tags = element.all(by.repeater('tag in tagList.items track by track(tag)'));
                expect(tags.count()).toBe(6);
                element(by.css('.save-question')).click();
                browser.sleep(10);
                expect(setQ.first().getText()).toBe(initialQuestion + 'fakeQuestion');
                expect(setQ.count()).toBe(6);
            });
        });

        it('Should show/hide "Clear Tag/Tags" button when user selects any tag' , function() {
            element(by.cssContainingText('.abn-tree .tree-label', 'Programming Languages')).click();
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
