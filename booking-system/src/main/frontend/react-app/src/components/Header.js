import Button from './Button';
import './Header.css'
import Title from './Title';

const Header = () => {
    return (
    <div className="Header">
    <header>
    <div className="primary_header" id="primHeader">
		<Title/>
    </div>
  </header>
    <nav className="secondary_header" id="secHeader">
      <ul>
        <li><Button color='white' text='Home' page=''/></li>
        <li><Button color='white' text='Home' page=''/></li>
        <li><Button color='white' text='Home' page=''/></li>
        <li><Button color='white' text='Home' page=''/></li>
        <li><Button color='white' text='Home' page=''/></li>
        <li><Button color='white' text='Log in' page='login'/></li>
      </ul>
    </nav>
    </div>
    );
}

export default Header;