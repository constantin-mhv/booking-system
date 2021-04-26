import logo from '../logo.svg';
import Title from '../components/Title'
import Button from '../components/Button'
import MenuScreen from './HomeScreen'
import ScreenManager from './ScreenManager'
import '../App.css'
import LoginScreen from './LoginScreen';
import InputBox from '../components/InputBox';

const RegisterScreen = () => {
  return (
    <div className="RegisterScreen">
    <h1>Register</h1>
      <InputBox buttonText="Register"/>
    </div>
  );
}

export default RegisterScreen;
