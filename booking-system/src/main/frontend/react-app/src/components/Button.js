import Proptypes from 'prop-types'
import '../App.css'
import { Link } from "react-router-dom";

const Button = ({ color, text, onClick, page }) => {
    return (<Link to={page}>
        <button onClick={onClick} style={{ backgroundColor: color }} className='btn'>{text}</button>
    </Link>
    )
}

Button.defaultProps = {
    color: 'grey',
    link: null,
}

Button.propTypes = {
    text: Proptypes.string,
    color: Proptypes.string,
    onClick: Proptypes.func.isRequired,
}

export default Button