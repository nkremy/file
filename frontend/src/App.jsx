import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [file, setFile] = useState(null);
  const [msg ,setMsg] = useState("")

  const API_URL = "http://localhost:8080/API/v1"

  const upload = async ()=>{
    const data = new FormData();
    data.append("file",file);
    const response = await fetch(`${API_URL}/upload`,{
      method:"POST",
      body:data
    });
    if(response.ok) setMsg("Fichier Recu !");
  }

  return (
    <>
      <div className="">
        <h2>gestion des fichier </h2>
        <div className="">
          <input type="file" name="" id="" onChange={e=>setFile(e.target.value)} />
          <button className="" onClick={upload}>Envoyer</button>
          
        </div>
        <p></p>

        <hr />

        <h3>Image</h3>
        <img src="" alt="" />
      </div>
    </>
  )
}

export default App
