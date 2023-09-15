import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from '../src/pages/user/Login';
import Regist from '../src/pages/user/Regist';
import Main from '../src/pages/main/Main';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/user/login" element={<Login />} />
        <Route path="/user/regist" element={<Regist />} />
      </Routes>
    </Router>
  );
}

export default App;
