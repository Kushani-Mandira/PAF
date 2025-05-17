import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import SideBar from '../../Components/SideBar/SideBar';
function UpdatePost() {
  const { id } = useParams(); // Get the post ID from the URL
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [existingMedia, setExistingMedia] = useState([]); // Initialize as an empty array
  const [newMedia, setNewMedia] = useState([]); // New media files to upload
  const [loading, setLoading] = useState(true); // Add loading state

  

  return (
    <div>
      <div className='continer'>
        <div>   <SideBar /></div>
        <div className='continSection'>
          <div className="from_continer">
            <p className="Auth_heading">Create New Post</p>
            <form onSubmit={handleSubmit} className='from_data'>
              <div className="Auth_formGroup">
                <label className="Auth_label">Title</label>
                <input
                  className="Auth_input"
                  type="text"
                  placeholder="Title"
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                  required
                />
              </div>
              <div className="Auth_formGroup">
                <label className="Auth_label">Description</label>
                <textarea
                  className="Auth_input"
                  placeholder="Description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                  required
                  rows={3}
                />
              </div>
              <div className="Auth_formGroup">
                <label className="Auth_label">Media</label>
                <div className='seket_media'>
                  {existingMedia.map((mediaUrl, index) => (
                    <div key={index}>
                      {mediaUrl.endsWith('.mp4') ? (
                        <video controls className='media_file_se'>
                          <source src={`http://localhost:8080${mediaUrl}`} type="video/mp4" />
                          Your browser does not support the video tag.
                        </video>
                      ) : (
                        <img className='media_file_se' src={`http://localhost:8080${mediaUrl}`} alt={`Media ${index}`} />
                      )}
                      <button
                      className='rem_btn'
                        onClick={() => handleDeleteMedia(mediaUrl)}

                      >
                        X
                      </button>
                    </div>
                  ))}
                </div>
                <input
                  className="Auth_input"
                  type="file"
                  accept="image/jpeg,image/png,image/jpg,video/mp4"
                  multiple
                  onChange={handleNewMediaChange}
                />
              </div>
              <button type="submit" className="Auth_button">Submit</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UpdatePost;
