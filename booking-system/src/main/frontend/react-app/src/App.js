import logo from './logo.svg';
import Title from './components/Title'
import Button from './components/Button'
import LoginScreen from './pages/LoginScreen'
import './App.css';
import ScreenManager from './pages/ScreenManager';

const appStateEnum = {
  LOG_IN: 0,
  MENU: 1
}
var appState = appStateEnum.LOG_IN;

const onc = () => {
  console.log("aaa")
}

const acdc = () => {
  appState = 1- appState
  console.log(appState)
}

function App() {
    return <div className="App"><ScreenManager/></div>;
  /* 
  appState = appState
  switch (appState) {
    case appStateEnum.LOG_IN: */
      /* return (
        <div className="App">
          <h1>Hello World!</h1>
          <Title />
          <Button color='green' text='Log in' onClick = {onc}/>
          <br/>
          <Button onClick = {acdc} text='AC/DC'/> */
          /*{/* <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header> *///}
       /*  </div>
      ); */
    /* default: */
      /* return (<div>a</div>); */
  /* } */
}

export default App;
