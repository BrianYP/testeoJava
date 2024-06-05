*** Settings ***
Library    SeleniumLibrary
Documentation    DataDriver Testing With Excel
Library    DataDriver    pruebaExcel.xlsx    sheet_name=Sheet1
Suite Setup    Open Browser    ${URL}    chrome
Suite Teardown    Close Browser
Test Setup    Launch URL
Test Template    Read All The Data

*** Variables ***
${URL}    https://demoqa.com/text-box
${FULLNAME}    id:userName
${EMAILADD}    id:userEmail
${CURRENTADD}    id:currentAddress
${PERMANENTADD}    id:permanentAddress
${SUBMIT_BTN}    id:submit
${NAMEVERIFY}    id:name

*** Test Cases ***
Verify All The Data From Excel
    [Template]    Read All The Data
    ${name}    ${email}    ${current}    ${permanent}

*** Keywords ***
Read All The Data
    [Arguments]    ${name}    ${email}    ${current}    ${permanent}
    Log To Console    ${name} : ${email} : ${current} : ${permanent}
    Input Text    ${FULLNAME}    ${name}
    Input Text    ${EMAILADD}    ${email}
    Input Text    ${CURRENTADD}    ${current}
    Input Text    ${PERMANENTADD}    ${permanent}
    Execute Javascript    window.scrollTo(0,document.body.scrollHeight)
    Click Element    ${SUBMIT_BTN}
    ${text}    Get Text    ${NAMEVERIFY}
    Should Be Equal As Strings    ${text}    Name:${name}

Launch URL
    Go To    ${URL}

