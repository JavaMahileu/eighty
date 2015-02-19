var HtmlReporter = require('protractor-html-screenshot-reporter');
exports.config = {
    specs : [ 'e2e/*.js' ],
    rootElement : 'body',
    baseUrl : 'http://localhost:8282',

    capabilities: {
        'browserName': 'firefox'
    },

    onPrepare: function() {
        jasmine.getEnv().addReporter(new HtmlReporter({
           baseDirectory: 'src/main/webapp/e2etestresults',
           takeScreenShotsOnlyForFailedSpecs: true,
           metaDataBuilder: function metaDataBuilder(spec, descriptions, results, capabilities) {
               console.log({
                  description: descriptions.join(' in '),
                  passed: results.passed()});
               return {
                  description: descriptions.join(' '),
                  passed: results.passed(),
                  browser: { name: capabilities.caps_.browserName, version: capabilities.caps_.version },
                  os: capabilities.caps_.platform,
                  message: results.items_[0].message
               };
            }
        }));
    },

    jasmineNodeOpts : {
        // onComplete will be called just before the driver quits.
        onComplete : null,
        // If true, display spec names.
        isVerbose : true,
        // If true, print colors to the terminal.
        showColors : false,
        // If true, include stack traces in failures.
        includeStackTrace : true,
        // Default time to wait in ms before a test fails.
        defaultTimeoutInterval : 30000
    }
};
