//create a common property
class Content{
    constructor(contentType){
        this.contentType = contentType;
    }
}


//create functions and extend common property

class htmlContent extends Content{
    constructor(tags){
        super("HTML");
        this.tags = tags;
    }
}

class cssContent extends Content{
    constructor(cssTargets){
        super("CSS");
        this.cssTargets = cssTargets;
    }
}

class textContent extends Content{
    constructor(lineNumber){
        super("TEXT");
        this.lineNumber = lineNumber;
    }
}

function countmatches(regex, content){
    const matches = content.match(regex);
    const counts = [];

    if(matches){
        matches.forEach(match => {
            counts[match] = (counts[match] || 0) + 1;
        });
    }
    return counts;
}


function detectContent(input){
    const HTMLRegex = /<\/?\w+/g;
    const CSSRegex = /[#.|]\w+/g;

    const htmlTags = countmatches(HTMLRegex,input);
    const cssTargets = countmatches(CSSRegex,input);
    
    if(Object.keys(htmlTags).length > 0){
        return new htmlContent(htmlTags);
    }else if(Object.keys(cssTargets).length > 0){
        return new cssContent(cssTargets);
    }else{
        const textCount = input.split('\n').length;
        return new textContent(textCount);
    }
}


const result1 = detectContent("this is a test\nSeems to work");
console.log(result1);


const result2 = detectContent(".class1 { color: red; } #id1 { font-size: 12px; } .class1 { padding: 5px; }")
console.log(result2);

const result3 = detectContent("<html><div></div><div></div></html>")
console.log(result3);

