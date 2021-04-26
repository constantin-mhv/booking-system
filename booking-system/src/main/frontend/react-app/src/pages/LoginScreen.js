import logo from '../logo.svg';
import Title from '../components/Title'
import Button from '../components/Button'
import MenuScreen from './HomeScreen'
import ScreenManager from './ScreenManager'
import '../App.css'
import InputBox from '../components/InputBox';

const LoginScreen = () => {
  return (
    <div className="App">
    <h1>Log in</h1>
      <Button color='yellow' text='Register' page='register'/>
      <InputBox/>
    </div>
  );
}

export default LoginScreen;
