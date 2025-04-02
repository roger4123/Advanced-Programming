<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }
        .images-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .image-card {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            background-color: #fff;
        }
        .image-card h3 {
            margin-top: 0;
            color: #444;
        }
        .image-info {
            color: #666;
            margin-bottom: 8px;
        }
        .tags {
            display: flex;
            flex-wrap: wrap;
            gap: 5px;
        }
        .tag {
            background-color: #e0f0ff;
            color: #0066cc;
            padding: 2px 8px;
            border-radius: 10px;
            font-size: 0.85em;
        }
        .footer {
            margin-top: 30px;
            color: #777;
            font-size: 0.9em;
            text-align: center;
            border-top: 1px solid #eee;
            padding-top: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>${title}</h1>

    <div class="images-grid">
        <#list images as image>
            <div class="image-card">
                <h3>${image.name()}</h3>
                <div class="image-info">Date: ${image.date()}</div>
                <div class="image-info">Path: ${image.location().getAbsolutePath()}</div>
                <div class="tags">
                    <#list image.tags() as tag>
                        <span class="tag">${tag}</span>
                    </#list>
                </div>
            </div>
        </#list>
    </div>

    <div class="footer">
        Generated on: ${generatedDate}
        <p>Total Images: ${images?size}</p>
    </div>
</div>
</body>
</html>