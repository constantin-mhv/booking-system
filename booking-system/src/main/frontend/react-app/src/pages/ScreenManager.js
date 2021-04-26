import React from 'react';
import { Switch, Route } from 'react-router-dom';
import { useState } from 'react'
import LoginScreen from './LoginScreen'
import MenuScreen from './HomeScreen'
import RegisterScreen from './RegisterScreen';

const screens = {
    "login": LoginScreen,
    "menu": MenuScreen,
}

const ScreenManager = () => {
    // const [screenState, setScreenState] = useState('login')
    /* screens[screenState]; */
    return (
    <Switch>
    <Route exact path='/' component={MenuScreen}></Route>
    <Route exact path='/register' component={RegisterScreen}></Route>
    <Route exact path='/login' component={LoginScreen}></Route>
    </Switch>
    );
    /* if (screenState == 'login') {
        return <LoginScreen/>
    }
    if (screenState == 'menu') {
        return <MenuScreen/>
    } */
}

export default ScreenManager;