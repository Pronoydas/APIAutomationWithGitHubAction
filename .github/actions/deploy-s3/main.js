const core = require('@actions/core');
const github = require('@actions/github');
const exec = require('@actions/exec');

function run() {
// Get input from action.yml file
    const bucketName=core.getInput('bucket' ,{required: true});
    const bucketRegion=core.getInput('bucket-region' ,{required: true});
    const distFolder=core.getInput('Test_Output' ,{required: true});
// Upload File
    const s3Uri = `s3://${bucketName}`;
    exec.exec(`aws s3 sync ${distFolder} ${s3Uri} --region ${bucketRegion}`)
// Printing text into github runner mc console
   core.notice("hello Its  from JS custom msg::")

}

run();
//
// To Create JS-Node Project 
// Frsit - Run - npm init -y will crate packegr.json and other file 
// Then install dependies -- npm install @actions/core @actions/github @actions/exec 
// above code to install git hub depen
