library 'hci@main'

node(){
    docker tagname: "mypipeline:1.0.0", dbuild: false, pullToRegistry: false
}