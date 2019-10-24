package org.net.perorin.tama.chang

static def getResourceURL(def resource){
    return Thread.currentThread().getContextClassLoader().getResource(resource)
}

static def getResourceStr(def resource){
    return Thread.currentThread().getContextClassLoader().getResource(resource).toString()
}

static def getResourceAsStream(def resource){
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)
}

static def getClassName() {
    return Thread.currentThread().getStackTrace()[2].getClassName()
}
