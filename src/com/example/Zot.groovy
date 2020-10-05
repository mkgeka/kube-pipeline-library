package com.example
def checkOutFrom(repo) {
  git url: "git@github.com:jenkinsci/${repo}"
}

return this
