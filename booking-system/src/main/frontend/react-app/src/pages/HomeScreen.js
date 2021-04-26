import logo from '../logo.svg';
import Header from '../components/Header'
import Title from '../components/Title'
import Button from '../components/Button'
import LoginScreen from './LoginScreen'
import '../App.css';

const toRegister = () => {
}

const acdc = () => {
  console.log("toLogin")
  LoginScreen()
  console.log("toLogin")
}

const MenuScreen = () => {
      return (
        <div className="MenuScreen">
          <h1>Home</h1>
          <Button color='green' text='Log in' page='login'/>
          <br/>
          {/* <header className="App-header">
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
      </header> */}
        </div>
      );
}

export default MenuScreen;
