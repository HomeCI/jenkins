def fileExists(String path, String fileName) {
    // Si el argumento path es igual a '.', establecer la ruta de la carpeta actual como directorio padre
    if (path == '.') {
        path = ''
    }
    
    // Crear un objeto File con la ruta relativa del archivo a verificar
    File file = new File(path, fileName)
    
    // Obtener la ruta absoluta del archivo
    String fullPath = file.getAbsolutePath()
    
    // Verificar si el archivo existe
    if (file.exists()) {
        println("El archivo " + fileName + " existe en la ruta " + fullPath)
        return true
    } else {
        println("El archivo " + fileName + " no existe en la ruta " + fullPath)
        return false
    }
}

def extractRepoName(url) {
    //Extrae el nombre de un repositorio a partir de su url.
    def matcher = url =~ /\/([^\/]+)\.git$/
    return matcher[0][1]
}